package chapter15.sec3

import arrow.Kind
import arrow.core.andThen
import arrow.higherkind
import chapter10.None
import chapter10.Option
import chapter10.Some
import chapter12.Either
import chapter12.Left
import chapter12.Right
import chapter12.fix
import chapter13.boilerplate.io.ForIO
import chapter13.boilerplate.io.IO
import chapter13.boilerplate.io.IOOf
import chapter13.boilerplate.io.fix
import chapter15.sec3.Process.Companion.Await
import chapter15.sec3.Process.Companion.Emit
import chapter15.sec3.Process.Companion.End
import chapter15.sec3.Process.Companion.Halt
import java.io.BufferedReader
import java.io.FileReader
import java.util.concurrent.ExecutorService

//tag::init1[]
@higherkind
sealed class Process<F, O> : ProcessOf<F, O> {
    companion object {
        data class Await<F, A, O>(
            val req: Kind<F, A>,
            val recv: (Either<Throwable, A>) -> Process<F, O> // <1>
        ) : Process<F, A>()

        data class Emit<F, O>(
            val head: O,
            val tail: Process<F, O>
        ) : Process<F, O>()

        data class Halt<F, O>(val err: Throwable) : Process<F, O>() // <2>

        object End : Exception() // <3>

        object Kill : Exception() // <4>
    }

    //tag::init2[]
    fun apply(p: () -> Process<F, O>): Process<F, O> =
        this.onHalt { ex: Throwable ->
            when (ex) {
                is End -> p() // <1>
                else -> Halt(ex) // <2>
            }
        }.fix()

    fun onHalt(f: (Throwable) -> ProcessOf<F, O>): ProcessOf<F, O> =
        when (this) {
            is Halt ->
                Try { f(this.err).fix() } // <3>
            is Emit ->
                Emit(this.head, tail.onHalt(f).fix())
            is Await<*, *, *> ->
                awaitAndThen<F, O, O>(req, recv) {
                    it.onHalt(f).fix()
                } //<4>
        }

    //end::init2[]
    //tag::init5[]
    fun <O2> flatMap(f: (O) -> Process<F, O2>): Process<F, O2> =
        when (this) {
            is Halt ->
                Halt(err)
            is Emit ->
                Try { f(head) }.apply { tail.flatMap(f) }
            is Await<*, *, *> -> {
                awaitAndThen<F, O, O2>(req, recv) { it.flatMap(f) }
            }
        }

    //end::init5[]
    //tag::ignore[]
    fun <O2> map(f: (O) -> O2): Process<F, O2> =
        when (this) {
            is Await<*, *, *> ->
                awaitAndThen<F, O, O2>(req, recv) { it.map(f) }
            is Emit ->
                Try { Emit(f(head), tail.map(f)) }
            is Halt ->
                Halt(err)
        }

    fun append(p: () -> Process<F, O>): Process<F, O> =
        this.onHalt { e ->
            when (e) {
                is End -> Try { p() }
                else -> Halt(e)
            }
        }.fix()

    //end::ignore[]
    //tag::init9[]
    fun onComplete(p: () -> Process<F, O>): Process<F, O> = // <1>
        this.onHalt { e: Throwable ->
            when (e) {
                is End -> p().asFinalizer() // <2>
                else -> p().asFinalizer().append { Halt(e) }
            }
        }.fix()
    //end::init9[]

    //tag::init10[]
    private fun asFinalizer(): Process<F, O> =
        when (this) {
            is Emit -> Emit(this.head, this.tail.asFinalizer())
            is Halt -> Halt(this.err)
            is Await<*, *, *> -> {
                await<F, O, O>(this.req) { ei: Either<Throwable, Nothing> ->
                    when (ei) {
                        is Left -> when (val e = ei.value) {
                            is Kill -> this.asFinalizer()
                            else -> this.recv(Left(e))
                        }
                        else -> this.recv(ei)
                    }
                }
            }
        }
    //end::init10[]
}

//end::init1[]
//tag::init3[]
fun <F, O> Try(p: () -> Process<F, O>): Process<F, O> =
    try {
        p()
    } catch (e: Throwable) {
        Halt(e)
    }
//end::init3[]

//tag::init4[]
fun <F, A, O> awaitAndThen(
    req: Kind<Any?, Any?>,
    recv: (Either<Throwable, Nothing>) -> Process<out Any?, out Any?>,
    fn: (Process<F, A>) -> Process<F, O>
): Process<F, O> =
    Await(
        req as Kind<F, Nothing>,
        recv as (Either<Throwable, A>) -> Process<F, A> andThen fn
    ).fix()
//end::init4[]

private fun <A> unsafePerformIO(
    ioa: IOOf<A>,
    pool: ExecutorService
): A = ioa.fix().run()

/*
//tag::init6[]
data class Await<ForIO, A, O>(
    val req: IO<A>,
    val recv: (Either<Throwable, A>) -> Process<ForIO, O>
) : Process<ForIO, O>()
//end::init6[]
 */

//tag::init7[]
fun <O> runLog(src: Process<ForIO, O>): IO<Sequence<O>> = IO {

    val E = java.util.concurrent.Executors.newFixedThreadPool(4)

    tailrec fun go(cur: Process<ForIO, O>, acc: Sequence<O>): Sequence<O> =
        when (cur) {
            is Emit ->
                go(cur.tail, acc + cur.head)
            is Halt ->
                when (val e = cur.err) {
                    is End -> acc
                    else -> throw e
                }
            is Await<*, *, *> -> {
                val re = cur.req as IO<O>
                val rcv = cur.recv
                    as (Either<Throwable, O>) -> Process<ForIO, O>
                val next: Process<ForIO, O> = try {
                    rcv(Right(unsafePerformIO(re, E)).fix())
                } catch (err: Throwable) {
                    rcv(Left(err))
                }
                go(next, acc)
            }
        }
    try {
        go(src, emptySequence())
    } finally {
        E.shutdown()
    }
}
//end::init7[]

fun <F, A, O> await(
    req: Kind<Any?, Any?>,
    recv: (Either<Throwable, Nothing>) -> Process<out Any?, out Any?>
): Process<F, O> =
    Await(
        req as Kind<F, Nothing>,
        recv as (Either<Throwable, A>) -> Process<F, A>
    ).fix()

const val fileName = "src/main/resources/samples/lines.txt"

//tag::init8[]
val p: Process<ForIO, String> =
    await<ForIO, BufferedReader, String>(
        IO { BufferedReader(FileReader(fileName)) }
    ) { ei1: Either<Throwable, BufferedReader> ->
        when (ei1) {
            is Right -> processNext(ei1)
            is Left -> Halt(ei1.value) // <1>
        }
    }

private fun processNext(ei1: Right<BufferedReader>): Process<ForIO, String> =
    await<ForIO, BufferedReader, String>(
        IO { ei1.value.readLine() }
    ) { ei2: Either<Throwable, String?> ->
        when (ei2) {
            is Right ->
                if (ei2.value == null) Halt(End) // <2>
                else Emit(ei2.value, processNext(ei1))
            is Left -> {
                await<ForIO, Nothing, Nothing>(
                    IO { ei1.value.close() }
                ) { Halt(ei2.value) } // <3>
            }
        }
    }
//end::init8[]

//tag::init11[]
fun <R, O> resource(
    acquire: IO<R>,
    use: (R) -> Process<ForIO, O>,
    release: (R) -> Process<ForIO, O>
): Process<ForIO, O> =
    await<ForIO, R, O>(acquire) { ei: Either<Throwable, R> ->
        when (ei) {
            is Right -> use(ei.value).onComplete { release(ei.value) }
            else -> release((ei as Right<R>).value)
        }
    }.fix()
//end::init11[]

fun <F, A> eval(fa: Kind<F, A>): Process<F, A> =
    await<F, A, A>(fa) { ea: Either<Throwable, Nothing> ->
        when (ea) {
            is Right<A> -> Emit(ea.value, Halt(End))
            is Left -> Halt(ea.value)
        }
    }

fun <F, A, B> eval_(fa: Kind<F, A>): Process<F, B> =
    eval(fa).drain()

fun <F, A, B> Process<F, A>.drain(): Process<F, B> =
    when (this) {
        is Halt -> Halt(this.err)
        is Emit -> this.tail.drain()
        is Await<*, *, *> ->
            awaitAndThen<F, A, B>(
                this.req,
                { ei: Either<Throwable, Nothing> -> this.recv(ei) },
                { it.drain() }
            )
    }

//tag::init12[]
fun lines(fileName: String): Process<ForIO, String> =
    resource(
        IO { BufferedReader(FileReader(fileName)) },
        { br: BufferedReader ->

            val iter = br.lines().iterator()

            fun step() = if (iter.hasNext()) Some(iter.next()) else None

            fun lns(): Process<ForIO, String> {
                return eval(IO { step() }).flatMap { ln: Option<String> ->
                    when (ln) {
                        is Some -> Emit(ln.get, lns())
                        is None -> Halt<ForIO, String>(End)
                    }
                }
            }

            lns()
        },
        { br: BufferedReader -> eval_(IO { br.close() }) }
    )
//end::init12[]

fun main() {
    runLog(p).run().forEach { println(it) }
}
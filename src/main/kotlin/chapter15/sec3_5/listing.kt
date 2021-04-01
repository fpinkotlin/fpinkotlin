package chapter15.sec3_5

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
import chapter15.sec3_3.Process
import chapter15.sec3_3.Process.Companion.Await
import chapter15.sec3_3.Process.Companion.Emit
import chapter15.sec3_3.Process.Companion.End
import chapter15.sec3_3.Process.Companion.Halt
import chapter15.sec3_3.Process.Companion.await1
import chapter15.sec3_3.Process.Companion.emit1
import chapter15.sec3_3.Process.Companion.eval
import chapter15.sec3_3.Process.Companion.evalDrain
import chapter15.sec3_3.Process1
import chapter15.sec3_4.tee
import chapter15.sec3_4.zipWith
import java.io.BufferedReader
import java.io.FileReader
import java.io.FileWriter
import java.util.concurrent.ExecutorService

//tag::init1[]
typealias Sink<F, O> = Process<F, (O) -> Process<F, Unit>>
//end::init1[]

fun <R, O> resource(
    acquire: IO<R>,
    use: (R) -> Process<ForIO, O>,
    release: (R) -> Process<ForIO, O>
): Process<ForIO, O> =
    eval(acquire).flatMap { r -> use(r).onComplete { release(r) } }

//tag::init2[]
fun fileW(file: String, append: Boolean = false): Sink<ForIO, String> =
    resource(
        acquire = IO { FileWriter(file, append) }, // <1>
        use = { fw: FileWriter ->
            constant { s: String ->
                eval(IO {
                    fw.write(s)
                    fw.flush()
                })
            } // <2>
        },
        release = { fw: FileWriter ->
            evalDrain(IO { fw.close() }) // <3>
        }
    )

fun <A> constant(a: A): Process<ForIO, A> = // <4>
    eval(IO { a }).flatMap { Emit(it, constant(it)) }
//end::init2[]

fun <F, O> join(p: Process<F, Process<F, O>>): Process<F, O> =
    p.flatMap { it }

//tag::init3[]
fun <F, I1, I2, O> zipWith(
    p1: Process<F, I1>,
    p2: Process<F, I2>,
    f: (I1, I2) -> O
): Process<F, O> =
    tee(p1, p2, zipWith(f))

fun <F, O> Process<F, O>.to(sink: Sink<F, O>): Process<F, Unit> =
    join(
        zipWith(this, sink) { o: O, fn: (O) -> Process<F, Unit> ->
            fn(o)
        }
    )
//end::init3[]

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
                        is None -> Halt<ForIO, String>(
                            End
                        )
                    }
                }
            }

            lns()
        },
        { br: BufferedReader -> evalDrain(IO { br.close() }) }
    )

fun fahrenheitToCelsius(f: Double): Double = (f - 32) * 5.0 / 9.0

fun <I> id(): Process1<I, I> = await1({ i: I -> Emit(i, id()) })

fun <I> intersperse(sep: I): Process1<I, I> =
    await1<I, I>({ i ->
        emit1<I, I>(i).append {
            id<I>().flatMap { i2 ->
                emit1<I, I>(
                    sep
                ).append { emit1<I, I>(i2) }
            }
        }
    })

//tag::init4[]
fun converter(inputFile: String, outputFile: String): Process<ForIO, Unit> =
    lines(inputFile)
        .filter { !it.startsWith("#") }
        .map { line -> fahrenheitToCelsius(line.toDouble()).toString() }
        .pipe(intersperse("\n"))
        .to(fileW(outputFile))
        .drain()
//end::init4[]

fun <A> unsafePerformIO(
    ioa: IOOf<A>,
    pool: ExecutorService
): A = ioa.fix().run()

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

fun main() {
    val fahrenheitFilename = "src/main/resources/fahrenheit.txt"
    val celsiusFilename = "build/celsius.txt"
    runLog(converter(fahrenheitFilename, celsiusFilename)).run()
}
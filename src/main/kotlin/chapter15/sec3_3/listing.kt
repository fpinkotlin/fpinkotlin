package chapter15.sec3_3

import arrow.Kind
import arrow.core.andThen
import arrow.higherkind
import chapter12.Either
import chapter12.Left
import chapter12.Right
import chapter12.fix

//tag::init1[]
@higherkind // <1>
class Is<I> : IsOf<I>
//end::init1[]

//tag::init2[]
typealias Process1<I, O> = Process<ForIs, O>
//end::init2[]

class ForProcess private constructor() {
    companion object
}
typealias ProcessOf<I, O> = arrow.Kind2<ForProcess, I, O>
typealias ProcessPartialOf<I> = arrow.Kind<ForProcess, I>

@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
inline fun <F, O> ProcessOf<F, O>.fix(): Process<F, O> =
    this as Process<F, O>

@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
inline fun <I, O> ProcessOf<I, O>.fix1(): Process1<I, O> =
    this as Process1<I, O>

@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
inline fun <I, O> Process1<I, O>.fix1(): Process<I, O> =
    this as Process<I, O>

sealed class Process<I, O> : ProcessOf<I, O> {

    companion object {

        //tag::init3[]
        data class Await<F, A, O>(
            val req: Kind<F, A>,
            val recv: (Either<Throwable, A>) -> Process<F, O>
        ) : Process<F, O>()
        //end::init3[]

        data class Emit<F, O>(
            val head: O,
            val tail: Process<F, O>
        ) : Process<F, O>()

        data class Halt<F, O>(val err: Throwable) : Process<F, O>()

        object End : Exception()

        object Kill : Exception()

        fun <F, O> Try(p: () -> Process<F, O>): Process<F, O> =
            try {
                p()
            } catch (e: Throwable) {
                Halt(e)
            }

        //tag::init4[]
        fun <I, O> await1(
            recv: (I) -> Process1<ForIs, O>,
            fallback: Process1<ForIs, O> = halt1<ForIs, O>()
        ): Process1<I, O> =
            Await(Is<I>()) { ei ->
                when (ei) {
                    is Left -> when (val err = ei.value) {
                        is End -> fallback
                        else -> Halt(err)
                    }
                    is Right -> Try { recv(ei.value) }
                }
            }

        fun <I, O> halt1(): Process1<ForIs, O> =
            Halt<ForIs, O>(End).fix1() // <1>

        fun <I, O> emit1(
            head: O,
            tail: Process1<ForIs, O> = halt1<ForIs, O>()
        ): Process1<ForIs, O> =
            Emit<ForIs, O>(
                head,
                tail.fix1() // <1>
            ).fix1() // <1>
        //end::init4[]

        //tag::init5[]
        fun <I, O> lift(f: (I) -> O): Process1<ForIs, O> =
            await1<I, O>({
                Emit<I, O>(f(it), Halt<ForIs, O>(End).fix1()).fix1()
            })

        fun <I> filter(f: (I) -> Boolean): Process1<ForIs, I> =
            await1<I, I>({ i ->
                if (f(i))
                    Emit<ForIs, I>(i, Halt<ForIs, I>(End)).fix1()
                else halt1<ForIs, I>()
            })
        //end::init5[]

        fun <I> take(n: Int): Process1<ForIs, I> =
            if (n <= 1) halt1<ForIs, I>()
            else await1<I, I>({ i -> emit1<I, I>(i, take(n - 1)) })

        fun <I> takeWhile(f: (I) -> Boolean): Process1<ForIs, I> =
            await1<I, I>({ i ->
                if (f(i)) emit1<I, I>(i)
                else halt1<I, I>()
            })

        fun <F, A, O> await(
            req: Kind<Any?, Any?>,
            recv: (Either<Throwable, Nothing>) -> Process<out Any?, out Any?>
        ): Process<F, O> = Await(
            req as Kind<F, A>,
            recv as (Either<Throwable, A>) -> Process<F, O>
        )

        fun <F, A, O> awaitAndThen(
            req: Kind<Any?, Any?>,
            recv: (Either<Throwable, Nothing>) -> Process<out Any?, out Any?>,
            fn: (Process<F, A>) -> Process<F, O>
        ): Process<F, O> =
            Await(
                req as Kind<F, Nothing>,
                recv as (Either<Throwable, A>) -> Process<F, A> andThen fn
            )

        fun <F, A> eval(fa: Kind<F, A>): Process<F, A> =
            await<F, A, A>(fa) { ea: Either<Throwable, Nothing> ->
                when (ea) {
                    is Right<A> -> Emit(ea.value, Halt(End))
                    is Left -> Halt(ea.value)
                }
            }

        fun <F, A, B> eval_(fa: Kind<F, A>): Process<F, B> =
            eval(fa).drain()
    }

    //tag::init6[]
    infix fun <O2> pipe(p2: Process1<O, O2>): Process<I, O2> =
        when (p2) {
            is Halt<*, *> ->
                this.kill<O2>() // <1>
                    .onHalt { e2 ->
                        Halt<I, O2>(p2.err).append {
                            Halt(e2) // <2>
                        }
                    }
            is Emit<*, *> -> {
                val h = p2.head as O2
                val t = p2.tail as Process<I, O2>
                Emit(h, this.pipe(t.fix1()))
            }
            is Await<*, *, *> -> {
                val rcv =
                    p2.recv as (Either<Throwable, O>) -> Process<I, O2>
                when (this) {
                    is Halt<*, *> ->
                        Halt<I, O2>(this.err) pipe
                            rcv(Left(this.err)).fix1() // <3>
                    is Emit<*, *> -> {
                        val h = this.head as O
                        val t = this.tail as Process<I, O>
                        t.pipe(Try { rcv(Right(h).fix()) }.fix1())
                    }
                    is Await<*, *, *> -> {
                        awaitAndThen<I, O, O2>(
                            req,
                            recv,
                            { it pipe p2 }
                        )
                    }
                }
            }
        }
    //end::init6[]

    //tag::init7[]
    fun <O2> kill(): Process<I, O2> =
        when (this) {
            is Await<*, *, *> -> {
                val rcv =
                    recv as (Either<Throwable, Nothing>) -> Process<I, O2>
                rcv(Left(Kill)).drain<O2>() // <1>
                    .onHalt { e ->
                        when (e) {
                            is Kill -> Halt(End) // <2>
                            else -> Halt(e)
                        }
                    }
            }
            is Halt<*, *> -> Halt(this.err)
            is Emit<*, *> -> {
                val t = this.tail as Process<I, O>
                t.kill()
            }
        }

    fun <O2> drain(): Process<I, O2> =
        when (this) {
            is Halt<*, *> -> Halt(this.err)
            is Emit<*, *> -> {
                val t = this.tail as Process<I, O2>
                t.drain()
            }
            is Await<*, *, *> -> {
                val re = req as Kind<I, O2>
                awaitAndThen<I, O2, O2>(re, recv) { it.drain() }
            }
        }
    //end::init7[]

    //tag::init8[]
    fun filter(f: (O) -> Boolean): Process<I, O> =
        this pipe Process.filter(f)
    //end::init8[]

    fun take(n: Int): Process<I, O> =
        this pipe Process.take(n)

    fun takeWhile(f: (O) -> Boolean): Process<I, O> =
        this pipe Process.takeWhile(f)

    //extras
    fun onHalt(f: (Throwable) -> Process<I, O>): Process<I, O> =
        when (this) {
            is Halt -> Try { f(this.err) }
            is Emit -> Emit(this.head, this.tail.onHalt(f))
            is Await<*, *, *> ->
                awaitAndThen<I, O, O>(req, recv) { it.onHalt(f) }
        }

    fun append(p: () -> Process<I, O>): Process<I, O> =
        this.onHalt { e ->
            when (e) {
                is End -> Try(p)
                else -> Halt(e)
            }
        }

    fun repeat(): Process<I, O> = this.append { this.repeat() }

    fun onComplete(p: () -> Process<I, O>): Process<I, O> =
        this.onHalt { e: Throwable ->
            when (e) {
                is End -> p().asFinalizer() // <2>
                else -> p().asFinalizer().append { Halt(e) }
            }
        }.fix()

    fun asFinalizer(): Process<I, O> =
        when (this) {
            is Emit -> Emit(this.head, this.tail.asFinalizer())
            is Halt -> Halt(this.err)
            is Await<*, *, *> -> {
                await<I, O, O>(this.req) { ei: Either<Throwable, Nothing> ->
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

    fun <O2> flatMap(f: (O) -> Process<I, O2>): Process<I, O2> =
        when (this) {
            is Halt ->
                Halt(err)
            is Emit ->
                Try { f(head) }.apply { tail.flatMap(f) }
            is Await<*, *, *> -> {
                awaitAndThen<I, O, O2>(req, recv) { it.flatMap(f) }
            }
        }

    fun <O2> map(f: (O) -> O2): Process<I, O2> = this pipe lift(f)
}

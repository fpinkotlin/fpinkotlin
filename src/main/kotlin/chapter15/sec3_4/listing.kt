package chapter15.sec3_4

import arrow.higherkind
import chapter12.Either
import chapter12.Left
import chapter12.Right
import chapter12.fix
import chapter15.sec3_3.Process
import chapter15.sec3_3.Process.Companion.Await
import chapter15.sec3_3.Process.Companion.Emit
import chapter15.sec3_3.Process.Companion.End
import chapter15.sec3_3.Process.Companion.Halt
import chapter15.sec3_3.Process.Companion.Try
import chapter15.sec3_3.Process.Companion.await
import chapter15.sec3_3.Process.Companion.awaitAndThen

//tag::init1[]
@higherkind
sealed class T<I1, I2, X> : TOf<I1, I2, X> {

    companion object {
        fun <I1, I2> left() = L<I1, I2>()
        fun <I1, I2> right() = R<I1, I2>()
    }

    abstract fun get(): Either<(I1) -> X, (I2) -> X>

    class L<I1, I2> : T<I1, I2, I1>() {
        override fun get(): Either<(I1) -> I1, (I2) -> I1> =
            Left { l: I1 -> l }
    }

    class R<I1, I2> : T<I1, I2, I2>() {
        override fun get(): Either<(I1) -> I2, (I2) -> I2> =
            Right { r: I2 -> r }.fix()
    }
}
//end::init1[]

//tag::init2[]
typealias Tee<I1, I2, O> = Process<ForT, O>
//end::init2[]

//tag::init3[]
fun <I1, I2, O> haltT(): Tee<I1, I2, O> = Halt(End)

fun <I1, I2, O> awaitL(
    fallback: Tee<I1, I2, O> = haltT<I1, I2, O>(),
    recv: (I1) -> Tee<I1, I2, O>
): Tee<I1, I2, O> =
    await<ForT, I1, O>(
        T.left<I1, I2>().get()
    ) { e: Either<Throwable, I1> ->
        when (e) {
            is Left -> when (val err = e.value) {
                is End -> fallback
                else -> Halt(err)
            }
            is Right -> Try { recv(e.value) }
        }
    }

fun <I1, I2, O> awaitR(
    fallback: Tee<I1, I2, O> = haltT<I1, I2, O>(),
    recv: (I2) -> Tee<I1, I2, O>
): Tee<I1, I2, O> =
    await<ForT, I1, O>(
        T.right<I1, I2>().get().fix()
    ) { e: Either<Throwable, I2> ->
        when (e) {
            is Left -> when (val err = e.value) {
                is End -> fallback
                else -> Halt(err)
            }
            is Right -> Try { recv(e.value) }
        }
    }

fun <I1, I2, O> emitT(
    h: O,
    tl: Tee<I1, I2, O> = haltT<I1, I2, O>()
): Tee<I1, I2, O> =
    Emit(h, tl)
//end::init3[]

//tag::init4[]
fun <I1, I2, O> zipWith(f: (I1, I2) -> O): Tee<I1, I2, O> =
    awaitL<I1, I2, O> { i1: I1 ->
        awaitR<I1, I2, O> { i2: I2 ->
            emitT<I1, I2, O>(
                f(i1, i2)
            )
        }
    }.repeat()

fun <I1, I2> zip(): Tee<I1, I2, Pair<I1, I2>> =
    zipWith { i1: I1, i2: I2 -> i1 to i2 }
//end::init4[]

//tag::init5[]
fun <F, O1, O2, O3> tee(
    p1: Process<F, O1>,
    p2: Process<F, O2>,
    t: Tee<O1, O2, O3>
): Process<F, O3> =
    when (t) {
        is Halt ->
            p1.kill<O3>() // <1>
                .onComplete { p2.kill() }
                .onComplete { Halt(t.err) }
        is Emit ->
            Emit(t.head, tee(p1, p2, t)) // <2>
        is Await<*, *, *> -> {
            val side = t.req as Either<(O1) -> O1, (O2) -> O2>
            val rcv =
                t.recv as (Either<Nothing, Any?>) -> Process<ForT, O3>

            when (side) { // <3>
                is Left -> when (p1) {
                    is Halt ->
                        p2.kill<O3>().onComplete { Halt(p1.err) } // <4>
                    is Emit ->
                        tee(p1.tail, p2, Try { rcv(Right(p1.head)) }) //<5>
                    is Await<*, *, *> -> { // <6>
                        awaitAndThen<F, O2, O3>(
                            p1.req, p1.recv, { tee(it, p2, t) })
                    }
                }
                is Right -> when (p2) { // <7>
                    is Halt -> p1.kill<O3>().onComplete { Halt(p2.err) }
                    is Emit ->
                        tee(p1, p2.tail, Try { rcv(Right(p2.head)) })
                    is Await<*, *, *> -> {
                        awaitAndThen<F, O2, O3>(
                            p2.req, p2.recv, { tee(p1, it, t) }
                        )
                    }
                }
            }
        }
    }
//end::init5[]

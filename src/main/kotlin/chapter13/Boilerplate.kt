package chapter13

import arrow.Kind
import chapter12.Functor
import chapter13.boilerplate.io.IO
import chapter5.Stream

interface Monad<F> : Functor<F> {

    fun <A> unit(a: A): Kind<F, A>

    fun <A, B> flatMap(fa: Kind<F, A>, f: (A) -> Kind<F, B>): Kind<F, B>

    fun <A> doWhile(
        fa: Kind<F, A>,
        cond: (A) -> Kind<F, Boolean>
    ): Kind<F, Unit>

    fun <A, B> forever(fa: Kind<F, A>): Kind<F, B>

    fun <A, B> foldM(
        sa: Stream<A>,
        z: B,
        f: (B, A) -> Kind<F, B>
    ): Kind<F, B>

    fun <A, B> foldDiscardM(
        sa: Stream<A>,
        z: B,
        f: (B, A) -> Kind<F, B>
    ): Kind<F, Unit>

    fun <A> foreachM(
        sa: Stream<A>,
        f: (A) -> Kind<F, Unit>
    ): Kind<F, Unit>

    fun <A> skip(fa: Kind<F, A>): Kind<F, Unit> = map(fa) { Unit }

    fun <A> sequenceDiscard(sa: Stream<Kind<F, A>>): Kind<F, Unit> =
        foreachM(sa) { a -> skip(a) }

    fun <A> whenM(ok: Boolean, f: () -> Kind<F, A>): Kind<F, Boolean>
}

fun stdin(): IO<String> = IO { readLine().orEmpty() }

fun stdout(msg: String): IO<Unit> = IO { println(msg) }

fun IntRange.toStream(): Stream<Int> {
    fun stream(from: Int, to: Int): Stream<Int> =
        when (from) {
            this.last + 1 ->
                Stream.empty()
            else ->
                Stream.cons({ from }, { stream(from + 1, to) })
        }
    return stream(this.first, this.last)
}
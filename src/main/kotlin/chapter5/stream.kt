package chapter5

import chapter4.Option
import chapter4.getOrElse

sealed class Stream<out A> {

    companion object {

        fun <A> cons(hd: () -> A, tl: () -> Stream<A>): Stream<A> {
            val head: A by lazy(hd)
            val tail: Stream<A> by lazy(tl)
            return Cons({ head }, { tail })
        }

        fun <A> of(vararg xs: A): Stream<A> =
            if (xs.isEmpty()) empty()
            else cons({ xs[0] },
                { of(*xs.sliceArray(1 until xs.size)) })

        fun <A> empty(): Stream<A> = Empty

        fun <A> continually(a: A): Stream<A> =
            cons({ a }, { continually(a) })

        fun <A, S> unfold(z: S, f: (S) -> Option<Pair<A, S>>): Stream<A> =
            f(z).map { pair ->
                cons({ pair.first },
                    { Stream.unfold(pair.second, f) })
            }.getOrElse {
                empty()
            }
    }

    fun <B> foldRight(z: () -> B, f: (A, () -> B) -> B): B =
        when (this) {
            is Cons -> f(this.head()) { this.tail().foldRight(z, f) }
            is Empty -> z()
        }
}

data class Cons<out A>(
    val head: () -> A,
    val tail: () -> Stream<A>
) : Stream<A>()

object Empty : Stream<Nothing>()

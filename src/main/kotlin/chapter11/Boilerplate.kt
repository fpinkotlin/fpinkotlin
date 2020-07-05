package chapter11

import arrow.higherkind
import chapter8.RNG
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

@higherkind
data class Gen<A>(val sample: State<RNG, A>) : GenOf<A> {
    companion object {
        fun <A> unit(a: A): Gen<A> = Gen(State.unit(a))
        fun string(): Gen<String> = TODO()
        fun double(rng: IntRange): Gen<Double> = TODO()
        fun choose(start: Int, end: Int): Gen<Int> = TODO()
    }
    fun <B> flatMap(f: (A) -> Gen<B>): Gen<B> = TODO()
    fun <B> map(f: (A) -> B): Gen<B> = TODO()
}

@higherkind
class Par<A>(run: (ExecutorService) -> Future<A>) : ParOf<A> {
    companion object {
        fun <A> unit(a: A): Par<A> = TODO()
    }
    fun <B> flatMap(f: (A) -> Par<B>): Par<B> = TODO()
}

@higherkind
data class State<S, out A>(val run: (S) -> Pair<A, S>) : StateOf<S, A> {

    companion object {
        fun <S, A> unit(a: A): State<S, A> =
            State { s: S -> Pair(a, s) }
    }

    fun <B> map(f: (A) -> B): State<S, B> =
        flatMap { a: A -> unit<S, B>(f(a)) }

    fun <B> flatMap(f: (A) -> State<S, B>): State<S, B> =
        State { s1: S ->
            val (a: A, s2: S) = this.run(s1)
            f(a).run(s2)
        }
}

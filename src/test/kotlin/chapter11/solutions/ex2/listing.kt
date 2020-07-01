package chapter11.solutions.ex2

import arrow.Kind
import arrow.Kind2
import chapter11.sec2.Monad

//tag::init[]
data class State<S, out A>(val run: (S) -> Pair<A, S>) : StateOf<S, A>

sealed class ForState private constructor() {
    companion object
}

typealias StateOf<S, A> = Kind2<ForState, S, A>

fun <S, A> StateOf<S, A>.fix() = this as State<S, A>

typealias StatePartialOf<S> = Kind<ForState, S>

interface StateMonad<S> : Monad<StatePartialOf<S>> {

    override fun <A> unit(a: A): StateOf<S, A>

    override fun <A, B> flatMap(
        fa: StateOf<S, A>,
        f: (A) -> StateOf<S, B>
    ): StateOf<S, B>
}
//end::init[]

package chapter11.solutions.ex2

import arrow.Kind2
import chapter11.Monad

data class State<S, out A>(val run: (S) -> Pair<A, S>) : StateOf<S, A>

class ForState private constructor() {
    companion object
}

typealias StateOf<S, A> = Kind2<ForState, S, A>

/*
class StateMonads<S> {
    val stateMonad = object : Monad<ForStateS> {
        override fun <A> unit(a: A): StateOf<S, A> = TODO()

        override fun <A, B> flatMap(
            fa: StateOf<S, A>,
            f: (A) -> StateOf<S, B>
        ): StateOf<S, B> = TODO()
    }
}
 */

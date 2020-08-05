package chapter11.exercises.ex17

import arrow.Kind
import chapter10.List
import chapter11.State
import chapter11.StateMonad
import chapter11.StateOf
import chapter11.StatePartialOf
import chapter11.fix

val intMonad: StateMonad<Int> = object : StateMonad<Int> {
    override fun <A> unit(a: A): StateOf<Int, A> =
        State { s -> Pair(a, s) }

    override fun <A, B> flatMap(
        fa: StateOf<Int, A>,
        f: (A) -> StateOf<Int, B>
    ): StateOf<Int, B> =
        fa.fix().flatMap { a -> f(a).fix() }
}

fun main() {

    val stateA: State<Int, Int> = State { a: Int -> Pair(a, 10 + a) }
    val stateB: State<Int, Int> = State { b: Int -> Pair(b, 10 * b) }

    //tag::init[]
    val replicateIntState: StateOf<Int, List<Int>> = TODO()

    val map2IntState: StateOf<Int, Int> = TODO()

    val sequenceIntState: StateOf<Int, List<Int>> = TODO()
    //end::init[]
}

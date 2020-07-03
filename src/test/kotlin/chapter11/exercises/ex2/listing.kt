package chapter11.exercises.ex2

import chapter11.solutions.ex2.StateOf

//tag::init[]
data class State<S, out A>(val run: (S) -> Pair<A, S>) : StateOf<S, A>
//end::init[]

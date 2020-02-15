package chapter8.exercises.ex4

import chapter6.Section_6_1.RNG

data class State<S, out A>(val run: (S) -> Pair<A, S>)

data class Gen<A>(val sample: State<RNG, A>)

//tag::init[]
fun choose(start: Int, stopExclusive: Int): Gen<Int> = TODO()
//end::init[]

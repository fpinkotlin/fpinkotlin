package chapter8.solutions.ex5

import chapter8.RNG
import chapter8.State

fun nextBoolean(rng: RNG): Pair<Boolean, RNG> {
    val (i1, rng2) = rng.nextInt()
    return Pair(i1 >= 0, rng2)
}

data class Gen<A>(val sample: State<RNG, A>) {
    companion object {

        fun <A> unit(a: A): Gen<A> = Gen(State.unit(a))

        fun boolean(): Gen<Boolean> =
            Gen(State { rng -> nextBoolean(rng) })

        fun <A> listOfN(n: Int, ga: Gen<A>): Gen<List<A>> =
            Gen(State.sequence(List(n) { ga.sample }))
    }
}

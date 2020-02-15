package chapter8.solutions.ex4

import chapter8.RNG
import chapter8.State

fun positiveInt(rng: RNG): Pair<Int, RNG> {
    val (i1, rng2) = rng.nextInt()
    return Pair(if (i1 < 0) -(i1 + 1) else i1, rng2)
}

data class Gen<A>(val sample: State<RNG, A>) {
    companion object {
        //tag::init[]
        fun choose(start: Int, stopExclusive: Int): Gen<Int> =
            Gen(State { rng: RNG -> positiveInt(rng) }
                .map { (start + it) % (stopExclusive - start) })
        //end::init[]
    }
}

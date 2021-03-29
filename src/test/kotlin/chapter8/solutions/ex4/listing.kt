package chapter8.solutions.ex4

import chapter8.RNG
import chapter8.State
import chapter8.double
import chapter8.nonNegativeInt

data class Gen<A>(val sample: State<RNG, A>) {
    companion object {
        //tag::init[]
        fun choose(start: Int, stopExclusive: Int): Gen<Int> =
            Gen(State { rng: RNG -> nonNegativeInt(rng) }
                .map { start + (it % (stopExclusive - start)) })
        //end::init[]

        //tag::init2[]
        fun chooseUnbiased(start: Int, stopExclusive: Int): Gen<Int> =
            Gen(State { rng: RNG -> double(rng) }
                .map { start + (it * (stopExclusive - start)) }
                .map { it.toInt() })
        //end::init2[]
    }
}

package chapter8.exercises.ex8

import chapter8.RNG
import chapter8.State

data class Gen<A>(val sample: State<RNG, A>) {
    companion object {
        //tag::init[]
        fun <A> weighted(
            ga: Pair<Gen<A>, Double>,
            gb: Pair<Gen<A>, Double>
        ): Gen<A> = TODO()
        //end::init[]
    }
}

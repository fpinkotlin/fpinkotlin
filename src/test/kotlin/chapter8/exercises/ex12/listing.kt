package chapter8.exercises.ex12

import chapter8.RNG
import chapter8.State
import utils.SOLUTION_HERE

data class SGen<A>(val forSize: (Int) -> Gen<A>)

data class Gen<A>(val sample: State<RNG, A>) {
    //tag::init[]
    fun listOf(): SGen<List<A>> =

        SOLUTION_HERE()
    //end::init[]
}

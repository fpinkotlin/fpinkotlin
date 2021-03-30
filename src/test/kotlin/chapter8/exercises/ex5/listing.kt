package chapter8.exercises.ex5

import chapter8.RNG
import chapter8.State
import utils.SOLUTION_HERE

data class Gen<A>(val sample: State<RNG, A>) {
    companion object {

        //tag::init[]
        fun <A> unit(a: A): Gen<A> =

            SOLUTION_HERE()

        fun boolean(): Gen<Boolean> =

            SOLUTION_HERE()

        fun <A> listOfN(n: Int, ga: Gen<A>): Gen<List<A>> =

            SOLUTION_HERE()
        //end::init[]
    }
}

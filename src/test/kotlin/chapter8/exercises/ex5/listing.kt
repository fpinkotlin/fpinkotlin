package chapter8.exercises.ex5

import chapter8.RNG
import chapter8.State

data class Gen<A>(val sample: State<RNG, A>) {
    companion object {

        //tag::init[]
        fun <A> unit(a: A): Gen<A> = TODO()

        fun boolean(): Gen<Boolean> = TODO()

        fun <A> listOfN(n: Int, ga: Gen<A>): Gen<List<A>> = TODO()
        //end::init[]
    }
}

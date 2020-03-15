package chapter8.exercises.ex6

import chapter8.RNG
import chapter8.State

//tag::init[]
data class Gen<A>(val sample: State<RNG, A>) {

    companion object {
        fun <A> listOfN(gn: Gen<Int>, ga: Gen<A>): Gen<List<A>> = TODO()
    }

    fun <B> flatMap(f: (A) -> Gen<B>): Gen<B> = TODO()
}
//end::init[]

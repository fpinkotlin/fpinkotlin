package chapter8.exercises.ex7

import chapter8.RNG
import chapter8.State

data class Gen<A>(val sample: State<RNG, A>) {
    companion object {
        //tag::init[]
        fun <A> union(ga: Gen<A>, gb: Gen<A>): Gen<A> = TODO()
        //end::init[]
    }
}

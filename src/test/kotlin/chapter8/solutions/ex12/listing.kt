package chapter8.solutions.ex12

import chapter8.RNG
import chapter8.State

data class Gen<A>(val sample: State<RNG, A>) {
    companion object {
        fun <A> listOfN(n: Int, ga: Gen<A>): Gen<List<A>> =
            Gen(State.sequence(List(n) { ga.sample }))
    }
}

data class SGen<A>(val forSize: (Int) -> Gen<A>) {
    companion object {
        //tag::init[]
        fun <A> listOf(ga: Gen<A>): SGen<List<A>> =
            SGen { i -> Gen.listOfN(i, ga) }
        //end::init[]
    }
}

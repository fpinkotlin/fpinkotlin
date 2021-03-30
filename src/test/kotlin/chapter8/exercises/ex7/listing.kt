package chapter8.exercises.ex7

import chapter8.RNG
import chapter8.State
import chapter8.nextBoolean
import utils.SOLUTION_HERE

data class Gen<A>(val sample: State<RNG, A>) {
    companion object {

        fun boolean(): Gen<Boolean> =
            Gen(State { rng -> nextBoolean(rng) })

        //tag::init[]
        fun <A> union(ga: Gen<A>, gb: Gen<A>): Gen<A> =

            SOLUTION_HERE()
        //end::init[]
    }

    fun <B> flatMap(f: (A) -> Gen<B>): Gen<B> =
        Gen(sample.flatMap { a -> f(a).sample })
}

package chapter8.exercises.ex8

import chapter8.RNG
import chapter8.State
import utils.SOLUTION_HERE

data class Gen<A>(val sample: State<RNG, A>) {
    companion object {

        //tag::init[]
        fun <A> weighted(
            pga: Pair<Gen<A>, Double>,
            pgb: Pair<Gen<A>, Double>
        ): Gen<A> =

            SOLUTION_HERE()
        //end::init[]
    }

    fun <B> flatMap(f: (A) -> Gen<B>): Gen<B> =
        Gen(sample.flatMap { a -> f(a).sample })
}

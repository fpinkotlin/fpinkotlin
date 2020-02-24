package chapter8.solutions.ex7

import chapter8.RNG
import chapter8.State
import chapter8.nextBoolean

data class Gen<A>(val sample: State<RNG, A>) {
    companion object {

        fun boolean(): Gen<Boolean> =
            Gen(State { rng -> nextBoolean(rng) })

        //tag::init[]
        fun <A> union(ga: Gen<A>, gb: Gen<A>): Gen<A> =
            boolean().flatMap { if (it) ga else gb }
        //end::init[]
    }

    fun <B> flatMap(f: (A) -> Gen<B>): Gen<B> =
        Gen(sample.flatMap { a -> f(a).sample })
}

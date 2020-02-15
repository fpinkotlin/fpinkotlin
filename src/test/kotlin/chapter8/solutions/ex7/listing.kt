package chapter8.solutions.ex7

import chapter8.RNG
import chapter8.State

fun nextBoolean(rng: RNG): Pair<Boolean, RNG> {
    val (i1, rng2) = rng.nextInt()
    return Pair(i1 >= 0, rng2)
}

data class Gen<A>(val sample: State<RNG, A>) {
    companion object {

        fun boolean(): Gen<Boolean> =
            Gen(State { rng -> nextBoolean(rng) })

        fun <A> union(ga: Gen<A>, gb: Gen<A>): Gen<A> =
            boolean().flatMap { if (it) ga else gb }
    }

    fun <B> flatMap(f: (A) -> Gen<B>): Gen<B> =
        Gen(sample.flatMap { a -> f(a).sample })
}

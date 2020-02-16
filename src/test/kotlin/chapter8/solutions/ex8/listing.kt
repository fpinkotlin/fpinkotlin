package chapter8.solutions.ex8

import chapter8.RNG
import chapter8.State

data class Gen<A>(val sample: State<RNG, A>) {
    companion object {

        fun probability(vararg p: Double): Gen<Int> =
                TODO("finish me!")

        fun <A> weighted(
            gap: Pair<Gen<A>, Double>,
            gbp: Pair<Gen<A>, Double>
        ): Gen<A> {
            val (ga, p1) = gap
            val (gb, p2) = gbp
            return Gen.probability(p1, p2).flatMap { p ->
                if (p == 1) ga else gb
            }
        }
    }

    fun <B> flatMap(f: (A) -> Gen<B>): Gen<B> =
        Gen(sample.flatMap { a -> f(a).sample })
}

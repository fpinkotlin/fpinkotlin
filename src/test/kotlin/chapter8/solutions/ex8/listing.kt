package chapter8.solutions.ex8

import chapter8.RNG
import chapter8.State

fun nonNegativeInt(rng: RNG): Pair<Int, RNG> {
    val (i1, rng2) = rng.nextInt()
    return Pair(if (i1 < 0) -(i1 + 1) else i1, rng2)
}

data class Gen<A>(val sample: State<RNG, A>) {
    companion object {

        fun probability(vararg p: Double): Gen<Int> =
            Gen(State { rng ->
                val total = p.sum()
                nonNegativeInt(rng)
                TODO("finish me!")
            })

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

package chapter8

import kotlin.math.absoluteValue

data class Gen<A>(val sample: State<RNG, A>) {
    companion object {
        fun <A> unit(a: A): Gen<A> = Gen(State.unit(a))

        fun choose(start: Int, stopExclusive: Int): Gen<Int> =
            Gen(State { rng: RNG -> nonNegativeInt(rng) }
                .map { start + (it % (stopExclusive - start)) })

        fun <A> listOfN(n: Int, ga: Gen<A>): Gen<List<A>> =
            Gen(State.sequence(List(n) { ga.sample }))

        fun <A> weighted(
            pga: Pair<Gen<A>, Double>,
            pgb: Pair<Gen<A>, Double>
        ): Gen<A> {
            val (ga, p1) = pga
            val (gb, p2) = pgb
            val prob =
                p1.absoluteValue /
                    (p1.absoluteValue + p2.absoluteValue)
            return Gen(State { rng: RNG -> double(rng) })
                .flatMap { d ->
                    if (d < prob) ga else gb
                }
        }
    }

    fun <B> flatMap(f: (A) -> Gen<B>): Gen<B> =
        Gen(sample.flatMap { a -> f(a).sample })

    fun <B> map(f: (A) -> B): Gen<B> = Gen(sample.map(f))
}

data class SGen<A>(val forSize: (Int) -> Gen<A>) {

    operator fun invoke(i: Int): Gen<A> = forSize(i)

    companion object {
        fun <A> listOf(ga: Gen<A>): SGen<List<A>> =
            SGen { i -> Gen.listOfN(i, ga) }
    }
}

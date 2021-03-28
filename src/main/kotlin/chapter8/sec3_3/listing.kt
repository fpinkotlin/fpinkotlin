package chapter8.sec3_3

import arrow.core.getOrElse
import arrow.core.toOption
import chapter8.Falsified
import chapter8.Passed
import chapter8.RNG
import chapter8.Result
import chapter8.State
import chapter8.TestCases
import chapter8.double
import chapter8.nonNegativeInt
import kotlin.math.absoluteValue
import kotlin.math.min

data class Gen<A>(val sample: State<RNG, A>) {
    companion object {
        fun <A> unit(a: A): Gen<A> = Gen(State.unit(a))

        fun choose(start: Int, stopExclusive: Int): Gen<Int> =
            Gen(State { rng: RNG -> nonNegativeInt(rng) }
                .map { start + (it % (stopExclusive - start)) })

        fun chooseUnbiased(start: Int, stopExclusive: Int): Gen<Int> =
            Gen(State { rng: RNG -> double(rng) }
                .map { start + (it * (stopExclusive - start)) }
                .map { it.toInt() })

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

//tag::init[]
typealias MaxSize = Int

data class Prop(val check: (MaxSize, TestCases, RNG) -> Result) {

    companion object {

        fun <A> forAll(g: SGen<A>, f: (A) -> Boolean): Prop =
            forAll({ i -> g(i) }, f) // <1>

        fun <A> forAll(g: (Int) -> Gen<A>, f: (A) -> Boolean): Prop =
            Prop { max, n, rng ->

                val casePerSize: Int = (n + (max - 1)) / max // <2>

                val props: Sequence<Prop> =
                    generateSequence(0) { it + 1 } // <3>
                        .take(min(n, max) + 1)
                        .map { i -> forAll(g(i), f) } // <4>

                val prop: Prop = props.map { p ->
                    Prop { max, _, rng ->
                        p.check(max, casePerSize, rng)
                    }
                }.reduce { p1, p2 -> p1.and(p2) } // <5>

                prop.check(max, n, rng) // <6>
            }

        //tag::ignore[]
        fun <A> forAll(ga: Gen<A>, f: (A) -> Boolean): Prop =
            Prop { _, n, rng ->
                randomSequence(ga, rng).mapIndexed { i, a ->
                        try {
                            if (f(a)) Passed else Falsified(
                                a.toString(),
                                i
                            )
                        } catch (e: Exception) {
                            Falsified(buildMessage(a, e), i)
                        }
                    }.take(n)
                    .find { it.isFalsified() }
                    .toOption()
                    .getOrElse { Passed }
            }

        private fun <A> randomSequence(
            ga: Gen<A>,
            rng: RNG
        ): Sequence<A> =
            sequence {
                val (a: A, rng2: RNG) = ga.sample.run(rng)
                yield(a)
                yieldAll(randomSequence(ga, rng2))
            }

        private fun <A> buildMessage(a: A, e: Exception) =
            """
            |test case: $a
            |generated and exception: ${e.message}
            |stacktrace:
            |${e.stackTrace.take(10).joinToString("\n")}
        """.trimMargin()
        //end::ignore[]
    }

    fun and(p: Prop): Prop =
        Prop { max, n, rng -> // <7>
            when (val prop = check(max, n, rng)) {
                is Passed -> p.check(max, n, rng)
                is Falsified -> prop
            }
        }
}
//end::init[]

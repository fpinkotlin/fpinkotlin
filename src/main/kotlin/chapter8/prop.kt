package chapter8

import arrow.core.getOrElse
import arrow.core.toOption
import kotlin.math.min

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

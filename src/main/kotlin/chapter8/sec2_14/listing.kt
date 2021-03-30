package chapter8.sec2_14

import arrow.core.getOrElse
import arrow.core.toOption
import chapter8.Falsified
import chapter8.Passed
import chapter8.RNG
import chapter8.Result
import chapter8.SimpleRNG
import chapter8.State
import chapter8.TestCases

data class Gen<A>(val sample: State<RNG, A>)

data class Prop(val check: (TestCases, RNG) -> Result) {

    companion object {

        //tag::init[]
        fun <A> forAll(ga: Gen<A>, f: (A) -> Boolean): Prop =
            Prop { n: TestCases, rng: RNG ->
                randomSequence(ga, rng).mapIndexed { i, a -> // <1>
                        try {
                            if (f(a)) Passed
                            else Falsified(a.toString(), i) // <2>
                        } catch (e: Exception) {
                            Falsified(buildMessage(a, e), i) // <3>
                        }
                    }.take(n)
                    .find { it.isFalsified() }
                    .toOption()
                    .getOrElse { Passed }
            }

        private fun <A> randomSequence(
            ga: Gen<A>,
            rng: RNG
        ): Sequence<A> = // <4>
            sequence {
                val (a: A, rng2: RNG) = ga.sample.run(rng)
                yield(a)
                yieldAll(randomSequence(ga, rng2))
            }

        private fun <A> buildMessage(a: A, e: Exception) = // <5>
            """
            |test case: $a
            |generated and exception: ${e.message}
            |stacktrace:
            |${e.stackTrace.joinToString("\n")}
        """.trimMargin()
        //end::init[]
    }
}

fun main() {
    fun nextInt(rng: RNG): Pair<Int, RNG> {
        val (i1, rng2) = rng.nextInt()
        return i1 to rng2
    }

    fun integerGen(): Gen<Int> = Gen(State { rng -> nextInt(rng) })

    //success
    println(
        Prop.forAll(integerGen()) { it >= -2015756020 }
            .check(50, SimpleRNG(42))
    )

    //legit failure
    println(
        Prop.forAll(integerGen()) { it >= -2015756020 }
            .check(100, SimpleRNG(42))
    )

    //exceptional failure
    println(
        Prop.forAll(integerGen()) { throw Exception("BLAM") }
            .check(100, SimpleRNG(42))
    )
}

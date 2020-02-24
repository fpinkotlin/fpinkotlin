package chapter8.sec3.listing3

import arrow.core.getOrElse
import arrow.core.toOption
import chapter8.RNG
import chapter8.State
import chapter8.nonNegativeInt
import kotlin.math.min

typealias TestCases = Int

sealed class Result {
    abstract fun isFalsified(): Boolean
}

object Passed : Result() {
    override fun isFalsified(): Boolean = false
    override fun toString(): String = "Passed"
}

typealias SuccessCount = Int
typealias FailedCase = String

data class Falsified(
    val failure: FailedCase,
    val successes: SuccessCount
) : Result() {
    override fun isFalsified(): Boolean = true
    override fun toString(): String = "Failed: $failure"
}

data class Gen<A>(val sample: State<RNG, A>) {
    companion object {
        fun choose(start: Int, stopExclusive: Int): Gen<Int> =
            Gen(State { rng: RNG -> nonNegativeInt(rng) }
                .map { (start + it) % (stopExclusive - start) })

        fun <A> listOfN(n: Int, ga: Gen<A>): Gen<List<A>> =
            Gen(State.sequence(List(n) { ga.sample }))
    }
}

data class SGen<A>(val forSize: (Int) -> Gen<A>) {

    operator fun invoke(i: Int): Gen<A> = forSize(i)

    companion object {
        fun <A> listOf(ga: Gen<A>): SGen<List<A>> =
            SGen { i -> Gen.listOfN(i, ga) }
    }
}

fun <A> forAll(ga: Gen<A>, f: (A) -> Boolean): Prop =
    Prop { _, n, rng ->
        randomSequence(ga, rng).mapIndexed { i, a ->
            try {
                if (f(a)) Passed else Falsified(a.toString(), i)
            } catch (e: Exception) {
                Falsified(buildMessage(a, e), i)
            }
        }.take(n)
            .find { it.isFalsified() }
            .toOption()
            .getOrElse { Passed }
    }

fun <A> randomSequence(ga: Gen<A>, rng: RNG): Sequence<A> =
    sequence {
        val (a: A, rng2: RNG) = ga.sample.run(rng)
        yield(a)
        yieldAll(randomSequence(ga, rng2))
    }

fun <A> buildMessage(a: A, e: Exception) =
    """
    |test case: $a
    |generated and exception: ${e.message}
    |stacktrace:
    |${e.stackTrace.take(10).joinToString("\n")}
""".trimMargin()

//tag::init[]
typealias MaxSize = Int

data class Prop(val run: (MaxSize, TestCases, RNG) -> Result) {
    fun and(p: Prop) =
        Prop { max, n, rng -> // <1>
            when (val prop = run(max, n, rng)) {
                is Passed -> p.run(max, n, rng)
                is Falsified -> prop
            }
        }
}

fun <A> forAll(g: (Int) -> Gen<A>, f: (A) -> Boolean): Prop =
    Prop { max, n, rng ->

        val casePerSize = (n + (max - 1)) / max // <2>

        val props = generateSequence(0) { it + 1 } // <3>
            .take(min(n, max) + 1)
            .map { i -> forAll(g(i), f) } // <4>

        val prop = props.map { p ->
            Prop { max, _, rng ->
                p.run(max, casePerSize, rng)
            }
        }.reduce { p1, p2 -> p1.and(p2) } // <5>

        prop.run(max, n, rng)
    }

fun <A> forAll(g: SGen<A>, f: (A) -> Boolean): Prop =
    forAll({ i -> g(i) }, f) // <6>
//end::init[]

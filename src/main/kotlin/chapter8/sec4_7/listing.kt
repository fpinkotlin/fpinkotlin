package chapter8.sec4_7

import chapter8.MaxSize
import chapter8.RNG
import chapter8.SimpleRNG
import chapter8.TestCases

sealed class Result

object Passed : Result()

//tag::init1[]
object Proved : Result()
//end::init1[]

typealias SuccessCount = Int
typealias FailedCase = String

data class Falsified(
    val failure: FailedCase,
    val successes: SuccessCount
) : Result()

//tag::init2[]
fun run(
    p: Prop,
    maxSize: Int = 100,
    testCases: Int = 100,
    rng: RNG = SimpleRNG(System.currentTimeMillis())
): Unit =
    when (val result = p.run(maxSize, testCases, rng)) {
        is Falsified ->
            println(
                "Falsified after ${result.successes} passed tests: " +
                        result.failure
            )
        is Passed ->
            println("OK, passed $testCases tests.")
        is Proved ->
            println("OK, proved property.")
    }
//end::init2[]

data class Prop(val run: (MaxSize, TestCases, RNG) -> Result) {
    //tag::init3[]
    fun and(p: Prop) =
        Prop { max, n, rng ->
            when (val prop = run(max, n, rng)) {
                is Falsified -> prop
                else -> p.run(max, n, rng) // <1>
            }
        }
    //end::init3[]
}

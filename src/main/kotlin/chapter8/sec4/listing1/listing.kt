package chapter8.sec4.listing1

import arrow.core.extensions.list.foldable.exists
import chapter8.RNG
import chapter8.SimpleRNG
import chapter8.sec3.listing3.*

//tag::init2[]
fun run(
    p: Prop,
    maxSize: Int = 100, // <1>
    testCases: Int = 100, // <2>
    rng: RNG = SimpleRNG(System.currentTimeMillis())
): Unit =
    when (val result = p.run(maxSize, testCases, rng)) {
        is Falsified -> // <3>
            println(
                "Falsified after ${result.successes} passed tests: " +
                        result.failure
            )
        is Passed -> // <4>
            println("OK, passed $testCases tests.")
    }
//end::init2[]

fun main() {
    //tag::init1[]
    val smallInt = Gen.choose(-10, 10)

    val maxProp = forAll(SGen.listOf(smallInt)) { ns ->
        val mx = ns.max() ?: throw IllegalStateException("max on empty list")
        !ns.exists { it > mx } // <1>
    }
    //end::init1[]
    run(maxProp)
}

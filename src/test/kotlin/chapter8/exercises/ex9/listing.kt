package chapter8.exercises.ex9

import chapter8.RNG
import utils.SOLUTION_HERE

typealias TestCases = Int

sealed class Result {
    abstract fun isFalsified(): Boolean
}

object Passed : Result() {
    override fun isFalsified(): Boolean = false
}

typealias SuccessCount = Int
typealias FailedCase = String

data class Falsified(
    val failure: FailedCase,
    val successes: SuccessCount
) : Result() {
    override fun isFalsified(): Boolean = true
}

//tag::init[]
data class Prop(val run: (TestCases, RNG) -> Result) {
    fun and(p: Prop): Prop =

        SOLUTION_HERE()

    fun or(p: Prop): Prop =

        SOLUTION_HERE()
}
//end::init[]

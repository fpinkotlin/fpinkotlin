package chapter8.exercises.ex9

import chapter8.RNG

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
    fun and(p: Prop): Prop = TODO()

    fun or(p: Prop): Prop = TODO()
}
//end::init[]

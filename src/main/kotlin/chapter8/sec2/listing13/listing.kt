package chapter8.sec2.listing13

interface RNG {
    fun nextInt(): Pair<Int, RNG>
}

typealias SuccessCount = Int
typealias FailedCase = String
typealias TestCases = Int

sealed class Result {
    abstract fun isFalsified(): Boolean
}

object Passed : Result() {
    override fun isFalsified(): Boolean = false
}

data class Falsified(
    val failure: FailedCase,
    val successes: SuccessCount
) : Result() {
    override fun isFalsified(): Boolean = true
}

//tag::init[]
data class Prop(val run: (TestCases, RNG) -> Result)
//end::init[]

package chapter8.sec2.listing12

typealias SuccessCount = Int
typealias FailedCase = String

//tag::init[]
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
//end::init[]

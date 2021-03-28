package chapter8.sec2_12

import chapter8.FailedCase
import chapter8.SuccessCount

//tag::init[]
sealed class Result { // <1>
    abstract fun isFalsified(): Boolean
}

object Passed : Result() { // <2>
    override fun isFalsified(): Boolean = false
}

data class Falsified( // <3>
    val failure: FailedCase,
    val successes: SuccessCount
) : Result() {
    override fun isFalsified(): Boolean = true
}
//end::init[]

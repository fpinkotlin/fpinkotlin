package chapter8.sec2.listing13

interface RNG {
    fun nextInt(): Pair<Int, RNG>
}

typealias TestCases = Int

sealed class Result {
    abstract fun isFalsified(): Boolean
}

//tag::init[]
data class Prop(val run: (TestCases, RNG) -> Result)
//end::init[]

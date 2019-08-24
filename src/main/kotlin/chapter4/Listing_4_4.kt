package chapter4

import chapter3.List
import kotlin.math.abs

object Listing_4_4 {

    private fun <A, B> Option<A>.map(f: (A) -> B): Option<B> = TODO()

    private fun <A, B, C> map2(a: Option<A>, b: Option<B>, f: (A, B) -> C): Option<C> = TODO()

    fun <A, B> lift(f: (A) -> B): (Option<A>) -> Option<B> = { oa -> oa.map(f) }

    val absO: (Option<Double>) -> Option<Double> = lift { abs(it) }

    /**
     * Top secret formula for computing an annual car
     * insurance premium from two key factors.
     */
    fun insuranceRateQuote(age: Int, numberOfSpeedingTickets: Int): Double = TODO()

    fun parseInsuranceQuote(age: String, numberOfSpeedingTickets: String): Option<Double> {
        val optAge: Option<Int> = Try { age.toInt() }
        val optTickets: Option<Int> = Try { numberOfSpeedingTickets.toInt() }

        //insuranceRateQuote(optAge, optTickets) <1>
        return map2(optAge, optTickets) { a, b -> insuranceRateQuote(a, b) }
    }

    fun <A> Try(a: () -> A): Option<A> =
            try {
                Some(a())
            } catch (e: Throwable) {
                None
            }

    fun <A> sequence(xs: List<Option<A>>): Option<List<A>> = TODO()

    fun <A, B> List<A>.map(f: (A) -> B): List<B> = TODO()

    fun parseInts(xs: List<String>): Option<List<Int>> =
            sequence(xs.map { str -> Try { str.toInt() } })

}


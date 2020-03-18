package chapter4

import chapter3.List

object Listing_4_4 {

    private fun <A, B> Option<A>.map(f: (A) -> B): Option<B> = TODO()

    private fun <A, B, C> map2(
        oa: Option<A>,
        ob: Option<B>,
        f: (A, B) -> C
    ): Option<C> = TODO()

    //tag::lift[]
    fun <A, B> lift(f: (A) -> B): (Option<A>) -> Option<B> =
        { oa -> oa.map(f) }
    //end::lift[]

    //tag::abs[]
    val absO: (Option<Double>) -> Option<Double> =
        lift { kotlin.math.abs(it) }
    //end::abs[]

    //tag::quote[]
    /**
     * Top secret formula for computing an annual car
     * insurance premium from two key factors.
     */
    fun insuranceRateQuote(
        age: Int,
        numberOfSpeedingTickets: Int
    ): Double = TODO()
    //end::quote[]

    //tag::quote2[]
    fun parseInsuranceQuote(
        age: String,
        speedingTickets: String
    ): Option<Double> {

        val optAge: Option<Int> = catches { age.toInt() }

        val optTickets: Option<Int> =
            catches { speedingTickets.toInt() }

        //tag::secondsolution[]
        return map2(optAge, optTickets) { a, t ->
            insuranceRateQuote(a, t)
        }
        //end::secondsolution[]
        //tag::firstsolution[]
        //return insuranceRateQuote(optAge, optTickets) <1>
        //end::firstsolution[]
    }

    //tag::catches[]
    fun <A> catches(a: () -> A): Option<A> = // <2>
        try {
            Some(a()) // <3>
        } catch (e: Throwable) { // <4>
            None
        }
    //end::catches[]
    //end::quote2[]

    fun <A> sequence(xs: List<Option<A>>): Option<List<A>> = TODO()

    fun <A, B> List<A>.map(f: (A) -> B): List<B> = TODO()

    //tag::parseints[]
    fun parseInts(xs: List<String>): Option<List<Int>> =
        sequence(xs.map { str -> catches { str.toInt() } })
    //end::parseints[]
}

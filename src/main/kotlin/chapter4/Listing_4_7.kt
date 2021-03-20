package chapter4

import arrow.core.Either
import arrow.core.extensions.fx
import chapter4.Listing_4_4.insuranceRateQuote

object Listing_4_7 {
    //tag::init[]
    suspend fun String.parseToInt(): Either<Throwable, Int> = // <1>
        Either.catch { this.toInt() } // <2>

    suspend fun parseInsuranceRateQuote( // <3>
        age: String,
        numberOfSpeedingTickets: String
    ): Either<Throwable, Double> {
        val ae = age.parseToInt() // <4>
        val te = numberOfSpeedingTickets.parseToInt()
        return Either.fx { // <5>
            val a = ae.bind() // <6>
            val t = te.bind()
            insuranceRateQuote(a, t) // <7>
        }
    }
    //end::init[]
}

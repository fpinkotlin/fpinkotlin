package chapter4

import arrow.core.Try
import arrow.core.Either
import arrow.core.extensions.fx
import chapter4.Listing_4_4.insuranceRateQuote

object Listing_4_7 {
    //tag::init[]
    fun parseInsuranceRateQuote(
            age: String,
            numberOfSpeedingTickets: String
    ): Either<Throwable, Double> =
            Try.fx {
                val (age) = Try { age.toInt() }
                val (tickets) = Try { numberOfSpeedingTickets.toInt() }
                insuranceRateQuote(age, tickets)
            }.toEither()
    //end::init[]
}
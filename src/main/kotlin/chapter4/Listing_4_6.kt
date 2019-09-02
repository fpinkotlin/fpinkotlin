package chapter4

import arrow.core.Try
import arrow.core.Either
import arrow.core.extensions.`try`.monad.binding
import chapter4.Listing_4_4.insuranceRateQuote

object Listing_4_6 {
    //tag::init[]
    fun parseInsuranceRateQuote(age: String, numberOfSpeedingTickets: String): Either<Throwable, Double> =
        binding {
            val (age) = Try { age.toInt() }
            val (tickets) = Try { numberOfSpeedingTickets.toInt() }
            insuranceRateQuote(age, tickets)
        }.toEither()
    //end::init[]
}
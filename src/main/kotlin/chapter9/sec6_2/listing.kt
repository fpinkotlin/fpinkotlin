package chapter9.sec6_2

import chapter9.sec5_1.Listing.Location
import chapter9.sec5_2.ParseError

//tag::init1[]
typealias Parser<A> = (Location) -> Result<A> // <1>

sealed class Result<out A>
data class Success<out A>(val a: A, val consumed: Int) : Result<A>() // <2>
data class Failure(val get: ParseError) : Result<Nothing>()
//end::init1[]

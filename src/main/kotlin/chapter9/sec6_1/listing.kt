package chapter9.sec6_1

import arrow.core.Either
import arrow.core.Left
import arrow.core.Right
import chapter9.sec5_1.Listing.Location
import chapter9.sec5_2.ParseError

interface Parsers {
    fun string(s: String): Parser<String>
}

//tag::init1[]
typealias Parser<A> = (String) -> Either<ParseError, A>
//end::init1[]

object MyParser : Parsers {

    //tag::init2[]
    override fun string(s: String): Parser<String> =
        { input: String ->
            if (input.startsWith(s))
                Right(s)
            else Left(Location(input).toError("Expected: $s")) // <1>
        }
    //end::init2[]

    //tag::init3[]
    private fun Location.toError(msg: String) = // <2>
        ParseError(listOf(this to msg))
    //end::init3[]
}

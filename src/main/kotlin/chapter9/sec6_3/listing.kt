package chapter9.sec6_3

import arrow.core.lastOrNone
import chapter9.sec5_1.Listing.Location
import chapter9.sec5_2.ParseError
import chapter9.sec6_2.Failure
import chapter9.sec6_2.Parser
import chapter9.sec6_2.Result
import chapter9.sec6_2.Success

infix fun <T> T.cons(la: List<T>): List<T> = listOf(this) + la

//tag::init1[]
fun ParseError.push(loc: Location, msg: String): ParseError =
    this.copy(stack = (loc to msg) cons this.stack)
//end::init1[]

//tag::init2[]
fun <A> scope(msg: String, pa: Parser<A>): Parser<A> =
    { state -> pa(state).mapError { pe -> pe.push(state, msg) } }
//end::init2[]

//tag::init3[]
fun <A> Result<A>.mapError(f: (ParseError) -> ParseError): Result<A> =
    when (this) {
        is Success -> this
        is Failure -> Failure(f(this.get))
    }
//end::init3[]

//tag::init4[]
fun <A> tag(msg: String, pa: Parser<A>): Parser<A> =
    { state ->
        pa(state).mapError { pe ->
            pe.tag(msg) // <1>
        }
    }
//end::init4[]

//tag::init5[]
fun ParseError.tag(msg: String): ParseError {

    val latest = this.stack.lastOrNone() // <1>

    val latestLocation = latest.map { it.first } // <2>

    return ParseError(latestLocation.map { it to msg }.toList()) // <3>
}
//end::init5[]

package chapter9.sec6_4

import chapter9.sec5_1.Listing.Location
import chapter9.sec5_2.ParseError

typealias Parser<A> = (Location) -> Result<A>

sealed class Result<out A>
data class Success<out A>(val a: A, val consumed: Int) : Result<A>()

//tag::init1[]
data class Failure(
    val get: ParseError,
    val isCommitted: Boolean
) : Result<Nothing>()
//end::init1[]

//tag::init2[]
fun <A> attempt(p: Parser<A>): Parser<A> = { s -> p(s).uncommit() }

fun <A> Result<A>.uncommit(): Result<A> =
    when (this) {
        is Failure ->
            if (this.isCommitted)
                Failure(this.get, false)
            else this
        is Success -> this
    }
//end::init2[]

//tag::init3[]
fun <A> or(pa: Parser<A>, pb: () -> Parser<A>): Parser<A> =
    { state ->
        when (val r: Result<A> = pa(state)) {
            is Failure ->
                if (!r.isCommitted) pb()(state) // <1>
                else r // <2>
            is Success -> r // <3>
        }
    }
//end::init3[]

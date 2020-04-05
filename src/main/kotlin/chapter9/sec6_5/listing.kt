package chapter9.sec6_5

import chapter9.sec5_1.Listing.Location
import chapter9.sec6_4.Failure
import chapter9.sec6_4.Parser
import chapter9.sec6_4.Result
import chapter9.sec6_4.Success

//tag::init1[]
fun <A, B> flatMap(pa: Parser<A>, f: (A) -> Parser<B>): Parser<B> =
    { state ->
        when (val result = pa(state)) {
            is Success ->
                f(result.a)(state.advanceBy(result.consumed)) // <1>
                    .addCommit(result.consumed != 0) // <2>
                    .advanceSuccess(result.consumed) // <3>
            is Failure -> result
        }
    }
//end::init1[]

//tag::init2[]
fun Location.advanceBy(n: Int): Location =
    this.copy(offset = this.offset + n)
//end::init2[]

//tag::init3[]
fun <A> Result<A>.addCommit(commit: Boolean): Result<A> =
    when (this) {
        is Failure ->
            Failure(this.get, this.isCommitted || commit)
        is Success -> this
    }
//end::init3[]

//tag::init4[]
fun <A> Result<A>.advanceSuccess(n: Int): Result<A> =
    when (this) {
        is Success ->
            Success(this.a, this.consumed + n) // <1>
        is Failure -> this // <2>
    }
//end::init4[]

package chapter8.sec2.listing8

import arrow.core.Either

//tag::init[]
//tag::success[]
typealias SuccessCount = Int
//end::success[]
//tag::failed[]
typealias FailedCase = String
//end::failed[]

class Prop {
    fun check(): Either<Pair<FailedCase, SuccessCount>, SuccessCount> =
        TODO()

    fun and(p: Prop): Prop = TODO()
}
//end::init[]

package chapter8.sec2.listing8

import arrow.core.Either

//tag::init[]
//tag::success[]
typealias SuccessCount = Int
//end::success[]
//tag::failed[]
typealias FailedCase = String
//end::failed[]

interface Prop {
    fun check(): Either<Pair<FailedCase, SuccessCount>, SuccessCount>
    fun and(p: Prop): Prop
}
//end::init[]

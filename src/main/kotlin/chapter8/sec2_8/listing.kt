package chapter8.sec2_8

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
    //tag::and[]
    fun and(p: Prop): Prop
    //end::and[]
}
//end::init[]

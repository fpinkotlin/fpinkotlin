package chapter8.sec2_7

import arrow.core.Either

//tag::init[]
typealias SuccessCount = Int

interface Prop {
    fun check(): Either<String, SuccessCount>
    fun and(p: Prop): Prop
}
//end::init[]

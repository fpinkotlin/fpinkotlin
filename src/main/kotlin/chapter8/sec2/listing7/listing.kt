package chapter8.sec2.listing7

import arrow.core.Either


//tag::init[]
typealias SuccessCount = Int

class Prop {
    fun check(): Either<String, SuccessCount> = TODO()
    fun and(p: Prop): Prop = TODO()
}
//end::init[]

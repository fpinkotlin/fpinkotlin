package chapter8.sec2.listing10

import arrow.core.Either

typealias SuccessCount = Int
typealias FailedCase = String

//tag::init[]
typealias TestCases = Int

typealias Result = Either<Pair<FailedCase, SuccessCount>, SuccessCount>

data class Prop(val run: (TestCases) -> Result)
//end::init[]

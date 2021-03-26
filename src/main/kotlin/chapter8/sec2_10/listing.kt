package chapter8.sec2_10

import arrow.core.Either
import chapter8.FailedCase
import chapter8.SuccessCount

//tag::init[]
typealias TestCases = Int

typealias Result = Either<Pair<FailedCase, SuccessCount>, SuccessCount>

data class Prop(val check: (TestCases) -> Result)
//end::init[]

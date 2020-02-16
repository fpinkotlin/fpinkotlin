package chapter8.sec2.listing11

import arrow.core.Option

typealias SuccessCount = Int
typealias FailedCase = String
typealias TestCases = Int

//tag::init[]
typealias Result = Option<Pair<FailedCase, SuccessCount>>

data class Prop(val run: (TestCases) -> Result)
//end::init[]

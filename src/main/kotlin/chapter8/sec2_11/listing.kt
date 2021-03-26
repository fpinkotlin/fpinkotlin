package chapter8.sec2_11

import arrow.core.Option
import chapter8.FailedCase
import chapter8.SuccessCount
import chapter8.TestCases

//tag::init[]
typealias Result = Option<Pair<FailedCase, SuccessCount>>

data class Prop(val check: (TestCases) -> Result)
//end::init[]

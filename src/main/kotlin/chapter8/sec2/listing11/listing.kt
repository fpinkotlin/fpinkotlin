package chapter8.sec2.listing11

import arrow.core.Option
import chapter8.sec2.FailedCase
import chapter8.sec2.SuccessCount
import chapter8.sec2.listing10.TestCases

//tag::init[]
typealias Result = Option<Pair<FailedCase, SuccessCount>>

data class Prop(val run: (TestCases) -> Result)
//end::init[]

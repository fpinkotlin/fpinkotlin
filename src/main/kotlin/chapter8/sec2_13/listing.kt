package chapter8.sec2_13

import chapter8.RNG
import chapter8.Result
import chapter8.TestCases

//tag::init[]
data class Prop(val check: (TestCases, RNG) -> Result)
//end::init[]

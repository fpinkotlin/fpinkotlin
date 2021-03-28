package chapter8.sec3_1

import chapter8.RNG
import chapter8.State

data class Gen<A>(val sample: State<RNG, A>)

//tag::init[]
data class SGen<A>(val forSize: (Int) -> Gen<A>)
//end::init[]

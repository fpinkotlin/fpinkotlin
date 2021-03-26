package chapter8.sec3_2

import chapter8.MaxSize
import chapter8.RNG
import chapter8.Result
import chapter8.State
import chapter8.TestCases

data class Gen<A>(val sample: State<RNG, A>)
data class SGen<A>(val forSize: (Int) -> Gen<A>)
data class Prop(val check: (MaxSize, TestCases, RNG) -> Result)

//tag::init[]
fun <A> forAll(g: SGen<A>, f: (A) -> Boolean): Prop = TODO()
//end::init[]

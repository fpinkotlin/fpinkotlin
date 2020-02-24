package chapter8.sec3.listing2

import chapter8.*

data class Gen<A>(val sample: State<RNG, A>)
data class SGen<A>(val forSize: (Int) -> Gen<A>)
data class Prop(val run: (MaxSize, TestCases, RNG) -> Result)

//tag::init[]
fun <A> forAll(g: SGen<A>, f: (A) -> Boolean): Prop = TODO()
//end::init[]

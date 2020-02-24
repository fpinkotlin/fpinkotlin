package chapter8.sec3.listing2

typealias MaxSize = Int
typealias TestCases = Int

interface RNG {
    fun nextInt(): Pair<Int, RNG>
}

sealed class Result

data class State<S, out A>(val run: (S) -> Pair<A, S>)
data class Gen<A>(val sample: State<RNG, A>)
data class SGen<A>(val forSize: (Int) -> Gen<A>)
data class Prop(val run: (MaxSize, TestCases, RNG) -> Result)

//tag::init[]
fun <A> forAll(g: SGen<A>, f: (A) -> Boolean): Prop = TODO()
//end::init[]

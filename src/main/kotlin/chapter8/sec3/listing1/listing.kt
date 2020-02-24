package chapter8.sec3.listing1

interface RNG {
    fun nextInt(): Pair<Int, RNG>
}

data class State<S, out A>(val run: (S) -> Pair<A, S>)

data class Gen<A>(val sample: State<RNG, A>)

//tag::init[]
data class SGen<A>(val forSize: (Int) -> Gen<A>)
//end::init[]

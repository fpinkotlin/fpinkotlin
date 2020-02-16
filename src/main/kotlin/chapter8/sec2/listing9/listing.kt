package chapter8.sec2.listing9

//tag::init[]
interface RNG {
    fun nextInt(): Pair<Int, RNG>
}

data class State<S, out A>(val run: (S) -> Pair<A, S>)

data class Gen<A>(val sample: State<RNG, A>)
//end::init[]

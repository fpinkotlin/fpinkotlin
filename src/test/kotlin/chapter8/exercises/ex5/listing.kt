package chapter8.exercises.ex5

import chapter8.RNG
import chapter8.State

fun nextBoolean(rng: RNG): Pair<Boolean, RNG> {
    val (i1, rng2) = rng.nextInt()
    return Pair(i1 >= 0, rng2)
}

data class Gen<A>(val sample: State<RNG, A>) {

    companion object {

        //tag::init[]
        fun <A> unit(a: A): Gen<A> = TODO()

        fun boolean(): Gen<Boolean> = TODO()

        fun <A> listOfN(n: Int, ga: Gen<A>): Gen<List<A>> = TODO()
        //end::init[]
    }
}

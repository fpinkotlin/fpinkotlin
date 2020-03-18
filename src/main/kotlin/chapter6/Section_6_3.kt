package chapter6

import chapter6.Section_6_1.RNG

object Section_6_3 {

    class Repository

    //tag::init[]
    class MutatingSequencer {
        private var repo: Repository = TODO()
        fun nextInt(): Int = TODO()
        fun nextDouble(): Double = TODO()
    }
    //end::init[]

    //tag::init2[]
    interface StateActionSequencer {
        fun nextInt(): Pair<Int, StateActionSequencer>
        fun nextDouble(): Pair<Double, StateActionSequencer>
    }
    //end::init2[]

    //tag::init3[]
    fun randomPair(rng: RNG): Pair<Int, Int> {
        val (i1, _) = rng.nextInt()
        val (i2, _) = rng.nextInt()
        return Pair(i1, i2)
    }
    //end::init3[]

    //tag::init4[]
    fun randomPair2(rng: RNG): Pair<Pair<Int, Int>, RNG> {
        val (i1, rng2) = rng.nextInt()
        val (i2, rng3) = rng2.nextInt() //<1>
        return Pair(Pair(i1, i2), rng3) // <2>
    }
    //end::init4[]
}

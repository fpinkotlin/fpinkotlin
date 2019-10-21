package chapter6

import chapter6.Listing_6_1.RNG

object Listing_6_3 {

    class FooState
    class Bar

    //tag::init[]
    class Foo1 {
        private var s: FooState = TODO()
        fun bar(): Bar = TODO()
        fun baz(): Int = TODO()
    }
    //end::init[]

    //tag::init2[]
    interface Foo2 {
        fun bar(): Pair<Bar, Foo2>
        fun baz(): Pair<Int, Foo2>
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
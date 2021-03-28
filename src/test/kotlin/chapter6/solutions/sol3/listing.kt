package chapter6.solutions.sol3

import chapter6.RNG
import chapter6.solutions.sol2.double
import chapter6.solutions.sol5.doubleR
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class Solution3 : WordSpec({

    //tag::init[]
    fun intDouble(rng: RNG): Pair<Pair<Int, Double>, RNG> {
        val (i, rng2) = rng.nextInt()
        val (d, rng3) = double(rng2)
        return Pair(Pair(i, d), rng3)
    }

    fun doubleInt(rng: RNG): Pair<Pair<Double, Int>, RNG> {
        val (id, rng2) = intDouble(rng)
        val (i, d) = id
        return Pair(Pair(d, i), rng2)
    }

    fun double3(rng: RNG): Pair<Triple<Double, Double, Double>, RNG> {
        val (d1, rng2) = doubleR(rng)
        val (d2, rng3) = doubleR(rng2)
        val (d3, rng4) = doubleR(rng3)
        return Pair(Triple(d1, d2, d3), rng4)
    }
    //end::init[]

    "intDouble" should {

        val doubleBelowOne =
            Int.MAX_VALUE.toDouble() / (Int.MAX_VALUE.toDouble() + 1)

        val unusedRng = object : RNG {
            override fun nextInt(): Pair<Int, RNG> = TODO()
        }

        val rng3 = object : RNG {
            override fun nextInt(): Pair<Int, RNG> =
                Pair(Int.MAX_VALUE, unusedRng)
        }

        val rng2 = object : RNG {
            override fun nextInt(): Pair<Int, RNG> =
                Pair(Int.MAX_VALUE, rng3)
        }

        val rng = object : RNG {
            override fun nextInt(): Pair<Int, RNG> =
                Pair(Int.MAX_VALUE, rng2)
        }

        "generate a pair of int and double" {
            val (id, _) = intDouble(rng)
            val (i, d) = id
            i shouldBe Int.MAX_VALUE
            d shouldBe doubleBelowOne
        }

        "generate a pair of double and int" {
            val (di, _) = doubleInt(rng)
            val (d, i) = di
            d shouldBe doubleBelowOne
            i shouldBe Int.MAX_VALUE
        }

        "generate a triple of double, double, double" {
            val (ddd, _) = double3(rng)
            val (d1, d2, d3) = ddd
            d1 shouldBe doubleBelowOne
            d2 shouldBe doubleBelowOne
            d3 shouldBe doubleBelowOne
        }
    }
})

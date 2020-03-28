package chapter6.solutions

import chapter6.RNG
import chapter6.Rand
import chapter6.map
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

//tag::init[]
val doubleR: Rand<Double> =
    map(::nonNegativeInt) { i ->
        i / (Int.MAX_VALUE.toDouble() + 1)
    }
//end::init[]

class Solution_6_5 : WordSpec({

    "doubleR" should {

        val unusedRng = object : RNG {
            override fun nextInt(): Pair<Int, RNG> = TODO()
        }

        """generate a max value approaching 1 based on
            Int.MAX_VALUE using Rand""" {

                val rngMax = object : RNG {
                    override fun nextInt(): Pair<Int, RNG> =
                        Pair(Int.MAX_VALUE, unusedRng)
                }

                doubleR(rngMax) shouldBe Pair(
                    0.9999999995343387,
                    unusedRng
                )
            }

        "generate a min value of 0 based on 0 using Rand" {
            val rngMin = object : RNG {
                override fun nextInt(): Pair<Int, RNG> =
                    Pair(0, unusedRng)
            }

            doubleR(rngMin) shouldBe Pair(0.0, unusedRng)
        }
    }
})

package chapter6.solutions

import chapter6.RNG
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

//tag::init[]
fun double(rng: RNG): Pair<Double, RNG> {
    val (i, rng2) = nonNegativeInt(rng)
    return Pair(i / (Int.MAX_VALUE.toDouble() + 1), rng2)
}
//end::init[]

class Solution_6_2 : WordSpec({

    "double" should {

        val unusedRng = object : RNG {
            override fun nextInt(): Pair<Int, RNG> = TODO()
        }

        "generate a max value approaching 1 based on Int.MAX_VALUE" {

            val rngMax = object : RNG {
                override fun nextInt(): Pair<Int, RNG> =
                    Pair(Int.MAX_VALUE, unusedRng)
            }

            double(rngMax) shouldBe Pair(
                0.9999999995343387,
                unusedRng
            )
        }

        "generate a min value of 0 based on 0" {
            val rngMin = object : RNG {
                override fun nextInt(): Pair<Int, RNG> =
                    Pair(0, unusedRng)
            }

            double(rngMin) shouldBe Pair(0.0, unusedRng)
        }
    }
})

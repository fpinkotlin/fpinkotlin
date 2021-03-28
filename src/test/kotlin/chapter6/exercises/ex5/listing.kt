package chapter6.exercises.ex5

import chapter6.RNG
import chapter6.Rand
// import chapter6.map
// import chapter6.solutions.sol1.nonNegativeInt
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

/**
 * TODO: Re-enable tests by removing `!` prefix!
 */
class Exercise5 : WordSpec({

    //tag::init[]
    val doubleR: Rand<Double> = TODO()
    //end::init[]

    "doubleR" should {

        val unusedRng = object : RNG {
            override fun nextInt(): Pair<Int, RNG> = TODO()
        }

        """!generate a max value approaching 1 based on
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

        "!generate a min value of 0 based on 0 using Rand" {

            val rngMin = object : RNG {
                override fun nextInt(): Pair<Int, RNG> =
                    Pair(0, unusedRng)
            }

            doubleR(rngMin) shouldBe Pair(0.0, unusedRng)
        }
    }
})

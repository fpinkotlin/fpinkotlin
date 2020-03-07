package chapter6.exercises

import chapter6.RNG
import chapter6.unusedRng
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

//tag::init[]
fun double(rng: RNG): Pair<Double, RNG> = TODO()
//end::init[]

/**
 * TODO: Re-enable tests by removing `!` prefix!
 */
class Exercise_6_2 : WordSpec({

    "double" should {

        "!generate a max value approaching 1 based on Int.MAX_VALUE" {

            val rngMax = object : RNG {
                override fun nextInt(): Pair<Int, RNG> =
                    Pair(Int.MAX_VALUE, unusedRng)
            }

            double(rngMax) shouldBe Pair(
                0.9999999995343387,
                unusedRng
            )
        }

        "!generate a min value of 0 based on 0" {
            val rngMin = object : RNG {
                override fun nextInt(): Pair<Int, RNG> =
                    Pair(0, unusedRng)
            }

            double(rngMin) shouldBe Pair(0.0, unusedRng)
        }
    }
})

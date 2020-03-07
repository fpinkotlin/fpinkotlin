package chapter6.exercises

import chapter6.RNG
import chapter6.unusedRng
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

//tag::init[]
fun nonNegativeInt(rng: RNG): Pair<Int, RNG> = TODO()
//end::init[]

/**
 * TODO: Re-enable tests by removing `!` prefix!
 */
class Exercise_6_1 : WordSpec({

    "nonNegativeInt" should {

        "!return 0 if nextInt() yields 0" {

            val rng0 = object : RNG {
                override fun nextInt(): Pair<Int, RNG> =
                    Pair(0, unusedRng)
            }

            nonNegativeInt(rng0) shouldBe Pair(0, unusedRng)
        }

        "!return Int.MAX_VALUE when nextInt() yields Int.MAX_VALUE" {

            val rngMax = object : RNG {
                override fun nextInt(): Pair<Int, RNG> =
                    Pair(Int.MAX_VALUE, unusedRng)
            }

            nonNegativeInt(rngMax) shouldBe Pair(
                Int.MAX_VALUE,
                unusedRng
            )
        }

        "!return Int.MAX_VALUE when nextInt() yields Int.MIN_VALUE" {

            val rngMin = object : RNG {
                override fun nextInt(): Pair<Int, RNG> =
                    Pair(Int.MIN_VALUE, unusedRng)
            }

            nonNegativeInt(rngMin) shouldBe Pair(
                Int.MAX_VALUE,
                unusedRng
            )
        }

        "!return 0 when nextInt() yields -1" {

            val rngNeg = object : RNG {
                override fun nextInt(): Pair<Int, RNG> =
                    Pair(-1, unusedRng)
            }

            nonNegativeInt(rngNeg) shouldBe Pair(0, unusedRng)
        }
    }
})

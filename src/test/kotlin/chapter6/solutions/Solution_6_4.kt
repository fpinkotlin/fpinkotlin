package chapter6.solutions

import chapter3.Cons
import chapter3.List
import chapter3.Nil
import chapter6.Listing_6_1.RNG
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

fun ints(count: Int, rng: RNG): Pair<List<Int>, RNG> {
    return if (count > 0) {
        val (i, r1) = rng.nextInt()
        val (xs, r2) = ints(count - 1, r1)
        Pair(Cons(i, xs), r2)
    } else Pair(Nil, rng)
}

class Solution_6_4 : WordSpec ({

    "ints" should {
        "generate a list of ints of a specified length" {
            val rng = object : RNG {
                override fun nextInt(): Pair<Int, RNG> {
                    return Pair(1, this)
                }
            }

            ints(5, rng) shouldBe Pair(List.of(1, 1, 1, 1, 1), rng)
        }
    }
})


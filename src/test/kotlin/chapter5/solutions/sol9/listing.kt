package chapter5.solutions.sol9

import chapter3.List
import chapter5.Stream
import chapter5.Stream.Companion.cons
import chapter5.solutions.sol1.toList
import chapter5.solutions.sol13.take
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class Solution9 : WordSpec({

    //tag::init[]
    fun from(n: Int): Stream<Int> =
        cons({ n }, { from(n + 1) })
    //end::init[]

    "from" should {
        "return a Stream of ever incrementing numbers" {
            from(5).take(5).toList() shouldBe List.of(5, 6, 7, 8, 9)
        }
    }
})

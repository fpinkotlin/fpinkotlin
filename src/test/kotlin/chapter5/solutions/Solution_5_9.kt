package chapter5.solutions

import chapter3.List
import chapter5.Stream
import chapter5.Stream.Companion.cons
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class Solution_5_9 : WordSpec({

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

package chapter5.solutions

import chapter3.List
import chapter5.Stream
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class Solution_5_8 : WordSpec({

    //tag::init[]
    fun <A> constant(a: A): Stream<A> =
        Stream.cons({ a }, { constant(a) })
    //end::init[]

    "constants" should {
        "return an infinite stream of a given value" {
            constant(1).take(5).toList() shouldBe
                List.of(1, 1, 1, 1, 1)
        }
    }
})

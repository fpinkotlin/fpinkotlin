package chapter5.solutions.sol8

import chapter3.List
import chapter5.Stream
import chapter5.solutions.sol1.toList
import chapter5.solutions.sol13.take
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class Solution8 : WordSpec({

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

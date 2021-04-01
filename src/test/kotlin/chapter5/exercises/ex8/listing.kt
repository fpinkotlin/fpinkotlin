package chapter5.exercises.ex8

import chapter3.List
import chapter5.Stream
import chapter5.toList
import chapter5.solutions.ex13.take
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import utils.SOLUTION_HERE

//TODO: Enable tests by removing `!` prefix
class Exercise8 : WordSpec({

    //tag::init[]
    fun <A> constant(a: A): Stream<A> =

        SOLUTION_HERE()
    //end::init[]

    "constants" should {
        "!return an infinite stream of a given value" {
            constant(1).take(5).toList() shouldBe
                List.of(1, 1, 1, 1, 1)
        }
    }
})

package chapter5.exercises

import chapter3.List
import chapter5.Stream
import chapter5.solutions.toList
import chapter5.solutions.take
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

/**
 * Re-enable the tests by removing the `!` prefix!
 */
class Exercise_5_8 : WordSpec({

    //tag::init[]
    fun <A> constant(a: A): Stream<A> = TODO()
    //end::init[]

    "constants" should {
        "!return an infinite stream of a given value" {
            constant(1).take(5).toList() shouldBe
                List.of(1, 1, 1, 1, 1)
        }
    }
})

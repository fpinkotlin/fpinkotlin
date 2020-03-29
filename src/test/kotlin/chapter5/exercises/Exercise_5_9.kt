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
class Exercise_5_9 : WordSpec({

    //tag::init[]
    fun from(n: Int): Stream<Int> = TODO()
    //end::init[]

    "from" should {
        "!return a Stream of ever incrementing numbers" {
            from(5).take(5).toList() shouldBe
                List.of(5, 6, 7, 8, 9)
        }
    }
})

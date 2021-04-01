package chapter5.exercises.ex9

import chapter3.List
import chapter5.Stream
import chapter5.toList
import chapter5.solutions.ex13.take
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import utils.SOLUTION_HERE

//TODO: Enable tests by removing `!` prefix
class Exercise9 : WordSpec({

    //tag::init[]
    fun from(n: Int): Stream<Int> =

        SOLUTION_HERE()
    //end::init[]

    "from" should {
        "!return a Stream of ever incrementing numbers" {
            from(5).take(5).toList() shouldBe
                List.of(5, 6, 7, 8, 9)
        }
    }
})

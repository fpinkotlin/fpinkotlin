package chapter2.exercises.ex3

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import utils.SOLUTION_HERE

//TODO: Enable tests by removing `!` prefix
class Exercise3 : WordSpec({
    // tag::init[]
    fun <A, B, C> curry(f: (A, B) -> C): (A) -> (B) -> C =

        SOLUTION_HERE()
    // end::init[]

    "curry" should {
        """!break down a function that takes multiple arguments into
            a series of functions that each take only oneargument""" {

            val f: (Int) -> (Int) -> String =
                curry { a: Int, b: Int -> "$a:$b" }
            val y = f(1)(2)
            val z = f(1)(3)
            y shouldBe "1:2"
            z shouldBe "1:3"
        }
    }
})

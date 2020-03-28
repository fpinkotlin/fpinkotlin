package chapter2.exercises

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class Exercise_2_3 : WordSpec({
    // tag::init[]
    fun <A, B, C> curry(f: (A, B) -> C): (A) -> (B) -> C = TODO()
    // end::init[]

    /**
     * Re-enable the tests by removing the `!` prefix!
     */
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

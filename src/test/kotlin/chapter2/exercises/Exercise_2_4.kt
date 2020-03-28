package chapter2.exercises

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class Exercise_2_4 : WordSpec({
    // tag::init[]
    fun <A, B, C> uncurry(f: (A) -> (B) -> C): (A, B) -> C = TODO()
    // end::init[]

    /**
     * Re-enable the tests by removing the `!` prefix!
     */
    "uncurry" should {
        """!take a function accepting two values and then apply that
            function to the components of the pair which is the
            second argument""" {

                val f: (Int, Int) -> String =
                    uncurry<Int, Int, String> { a -> { b -> "$a:$b" } }
                f(1, 2) shouldBe "1:2"
                f(1, 3) shouldBe "1:3"
            }
    }
})

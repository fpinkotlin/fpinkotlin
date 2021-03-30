package chapter2.exercises.ex5

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import utils.SOLUTION_HERE

//TODO: Enable tests by removing `!` prefix
class Exercise5 : WordSpec({
    // tag::init[]
    fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C =

        SOLUTION_HERE()
    // end::init[]

    "compose" should {
        "!apply function composition over two functions" {
            val fahrenheit2celsius: (Double) -> String =
                compose<Double, Double, String>(
                    { b -> "$b degrees celsius" },
                    { a -> (a - 32.0) * (5.0 / 9.0) }
                )

            fahrenheit2celsius(68.0) shouldBe "20.0 degrees celsius"
        }
    }
})

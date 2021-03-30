package chapter10.exercises.ex18

import chapter10.Monoid
import chapter10.stringMonoid
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import utils.SOLUTION_HERE

//tag::init1[]
fun <A, B> functionMonoid(b: Monoid<B>): Monoid<(A) -> B> =

    SOLUTION_HERE()
//end::init1[]

//TODO: Enable tests by removing `!` prefix
class Exercise18 : WordSpec({

    "functionMonoid" should {
        "!combine the results of two functions using another monoid" {
            assertAll<Int> { i ->

                val fm = functionMonoid<Int, String>(stringMonoid)

                fm.combine(
                    { a -> "x$a" },
                    { a -> "y$a" })(i) shouldBe "x${i}y$i"
            }
        }
    }
})

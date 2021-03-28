package chapter3.exercises.ex8

import chapter3.List
import chapter3.Nil
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

// tag::init[]
fun <A> length(xs: List<A>): Int = TODO()
// end::init[]

class Exercise8 : WordSpec({
    "list length" should {
        "!calculate the length" {
            length(List.of(1, 2, 3, 4, 5)) shouldBe 5
        }

        "!calculate zero for an empty list" {
            length(Nil) shouldBe 0
        }
    }
})

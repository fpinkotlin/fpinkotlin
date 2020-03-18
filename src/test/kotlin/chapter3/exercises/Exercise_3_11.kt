package chapter3.exercises

import chapter3.List
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

// tag::init[]
fun <A> reverse(xs: List<A>): List<A> = TODO()
// end::init[]

class Exercise_3_11 : WordSpec({
    "list reverse" should {
        "!reverse list elements" {
            reverse(List.of(1, 2, 3, 4, 5)) shouldBe
                List.of(5, 4, 3, 2, 1)
        }
    }
})

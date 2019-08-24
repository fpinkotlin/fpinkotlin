package chapter3.exercises

import chapter3.List
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

// tag::init[]
fun sumL(xs: List<Int>): Int = TODO()

fun productL(xs: List<Double>): Double = TODO()

fun <A> lengthL(xs: List<A>): Int = TODO()
// end::init[]

class Exercise_3_10 : WordSpec({
    "!list sumL" should {
        "add all integers" {
            chapter3.solutions.sumL(List.of(1, 2, 3, 4, 5)) shouldBe 15
        }
    }

    "!list productL" should {
        "multiply all doubles" {
            chapter3.solutions.productL(List.of(1.0, 2.0, 3.0, 4.0, 5.0)) shouldBe 120.0
        }
    }

    "!list lengthL" should {
        "count the list elements" {
            chapter3.solutions.lengthL(List.of(1, 2, 3, 4, 5)) shouldBe 5
        }
    }
})
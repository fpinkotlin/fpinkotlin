package chapter3.exercises

import chapter3.Cons
import chapter3.List
import chapter3.Nil
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.WordSpec

// tag::init[]
fun <A> init(l: List<A>): List<A> = TODO()
// end::init[]

class Exercise_3_5 : WordSpec({

    "list init" should {
        "!return all but the last element" {
            chapter3.solutions.init(List.of(1, 2, 3, 4, 5)) shouldBe List.of(1, 2, 3, 4)
        }

        "!return Nil if only one element exists" {
            chapter3.solutions.init(List.of(1)) shouldBe Nil
        }

        "!throw an exception if no elements exist" {
            shouldThrow<IllegalStateException> {
                chapter3.solutions.init(List.empty<Int>())
            }
        }
    }
})
package chapter3.exercises.ex1

import chapter3.List
import chapter3.Nil
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.WordSpec

// tag::init[]
fun <A> tail(xs: List<A>): List<A> = TODO()
// end::init[]

class Exercise1 : WordSpec({
    "list tail" should {
        "!return the the tail when present" {
            tail(List.of(1, 2, 3, 4, 5)) shouldBe
                List.of(2, 3, 4, 5)
        }

        "!throw an illegal state exception when no tail is present" {
            shouldThrow<IllegalStateException> {
                tail(Nil)
            }
        }
    }
})

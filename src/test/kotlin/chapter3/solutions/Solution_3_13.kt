package chapter3.solutions

import chapter3.Cons
import chapter3.List
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

// tag::init[]
fun <A> append(
    a1: List<A>,
    a2: List<A>
): List<A> =
    foldRight(a1, a2, { x, y -> Cons(x, y) })
// end::init[]

class Solution_3_13 : WordSpec({
    "list append" should {
        "append two lists to each other" {
            append(
                List.of(1, 2, 3),
                List.of(4, 5, 6)
            ) shouldBe List.of(1, 2, 3, 4, 5, 6)
        }
    }
})

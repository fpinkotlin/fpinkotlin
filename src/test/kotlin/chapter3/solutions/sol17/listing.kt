package chapter3.solutions.sol17

import chapter3.Cons
import chapter3.List
import chapter3.foldRightL
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

// tag::init[]
fun <A, B> map(xs: List<A>, f: (A) -> B): List<B> =
    foldRightL(xs, List.empty()) { a: A, xa: List<B> ->
        Cons(f(a), xa)
    }
// end::init[]

class Solution17 : WordSpec({
    "list map" should {
        "apply a function to every list element" {
            map(List.of(1, 2, 3, 4, 5)) { it * 10 } shouldBe
                List.of(10, 20, 30, 40, 50)
        }
    }
})

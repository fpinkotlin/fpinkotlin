package chapter3.solutions

import chapter3.Cons
import chapter3.List
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

// tag::init[]
fun <A> filter(xs: List<A>, f: (A) -> Boolean): List<A> =
    foldRight(
        xs,
        List.empty(),
        { a, ls ->
            if (f(a)) Cons(a, ls)
            else ls
        })
// end::init[]

class Solution_3_18 : WordSpec({
    "list filter" should {
        "filter out elements not compliant to predicate" {
            filter(
                List.of(1, 2, 3, 4, 5)
            ) { it % 2 == 0 } shouldBe List.of(2, 4)
        }
    }
})

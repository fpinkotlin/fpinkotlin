package chapter3.solutions.sol19

import chapter3.Cons
import chapter3.List
import chapter3.append
import chapter3.foldRight
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

// tag::init[]
fun <A, B> flatMap(xa: List<A>, f: (A) -> List<B>): List<B> =
    foldRight(
        xa,
        List.empty(),
        { a, lb ->
            append(f(a), lb)
        })

fun <A, B> flatMap2(xa: List<A>, f: (A) -> List<B>): List<B> =
    foldRight(
        xa,
        List.empty(),
        { a, xb ->
            foldRight(f(a), xb, { b, lb -> Cons(b, lb) })
        })
// end::init[]

class Solution19 : WordSpec({
    "list flatmap" should {
        "map and flatten a list" {

            flatMap(List.of(1, 2, 3)) { i ->
                List.of(i, i)
            } shouldBe List.of(1, 1, 2, 2, 3, 3)

            flatMap2(List.of(1, 2, 3)) { i ->
                List.of(i, i)
            } shouldBe List.of(1, 1, 2, 2, 3, 3)
        }
    }
})

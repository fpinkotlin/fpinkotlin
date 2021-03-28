package chapter3.solutions.sol4

import chapter3.Cons
import chapter3.List
import chapter3.Nil
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

// tag::init[]
tailrec fun <A> dropWhile(l: List<A>, f: (A) -> Boolean): List<A> =
    when (l) {
        is Cons ->
            if (f(l.head)) dropWhile(l.tail, f) else l
        is Nil -> l
    }
// end::init[]

class Solution4 : WordSpec({

    "list dropWhile" should {
        val xs = List.of(1, 2, 3, 4, 5)
        "drop elements until predicate is no longer satisfied" {
            dropWhile(xs) { it < 4 } shouldBe List.of(4, 5)
        }

        "drop no elements if predicate never satisfied" {
            dropWhile(xs) { it == 100 } shouldBe xs
        }

        "drop all elements if predicate always satisfied" {
            dropWhile(xs) { it < 100 } shouldBe List.of()
        }

        "return Nil if input is empty" {
            dropWhile(List.empty<Int>()) { it < 100 } shouldBe Nil
        }
    }
})

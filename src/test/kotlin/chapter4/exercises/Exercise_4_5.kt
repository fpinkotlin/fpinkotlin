package chapter4.exercises

import chapter3.listings.Cons
import chapter3.listings.List
import chapter3.listings.Nil
import chapter4.None
import chapter4.Option
import chapter4.Some
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class Exercise_4_5 : WordSpec({

    fun <A, B> traverse(xa: List<A>, f: (A) -> Option<B>): Option<List<B>> = TODO()

    fun <A> sequence(xs: List<Option<A>>): Option<List<A>> = TODO()

    fun <A> Try(a: () -> A): Option<A> =
            try {
                Some(a())
            } catch (e: Throwable) {
                None
            }

    "traverse" should {
        "return some option of a transformed list if all transformations succeed" {
            val xa = List.of(1, 2, 3, 4, 5)
            traverse(xa) { a: Int -> Try { a.toString() } } shouldBe Some(List.of("1", "2", "3", "4", "5"))
        }

        "return a none option if any transformations fail" {
            val xa = List.of("1", "2", "x", "4")
            traverse(xa) { a -> Try { a.toInt() } } shouldBe None
        }
    }

    "sequence" should {
        "turn a list of some options into an option of list" {
            val lo = List.of(Some(10), Some(20), Some(30))
            sequence(lo) shouldBe Some(List.of(10, 20, 30))
        }

        "turn a list of options containing a none into a none" {
            val lo = List.of(Some(10), None, Some(30))
            sequence(lo) shouldBe None
        }
    }
})


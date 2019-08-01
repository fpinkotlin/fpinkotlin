package chapter3

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.WordSpec

class ExerciseSpec : WordSpec({

    val xs = List.of(1, 2, 3, 4, 5)

    "List.tail" should {
        "return the the tail when present" {
            tail(xs) shouldBe List.of(2, 3, 4, 5)
        }

        "throw an illegal state exception when no tail is present" {
            shouldThrow<IllegalStateException> {
                tail(Nil)
            }
        }
    }

    "List.setHead" should {
        "return a new List with a replaced head" {
            setHead(xs, 6) shouldBe List.of(6, 2, 3, 4, 5)
        }

        "throw an illegal state exception when no head is present" {
            shouldThrow<IllegalStateException> {
                setHead(Nil, 6)
            }
        }
    }

    "List.drop" should {
        "drop a given number of elements within capacity" {
            drop(xs, 3) shouldBe List.of(4, 5)
        }

        "drop a given number of elements up to capacity" {
            drop(xs, 5) shouldBe Nil
        }

        "throw an illegal state exception when dropped elements exceed capacity" {
            shouldThrow<IllegalStateException> {
                drop(xs, 6)
            }
        }
    }

    "List.dropWhile" should {
        "drop elements until predicate is no longer satisfied" {
            dropWhile(xs) { it < 4 } shouldBe List.of(4, 5)
        }

        "drop no elements if predicate never satisfied" {
            dropWhile(xs) { it == 100 } shouldBe List.of(1, 2, 3, 4, 5)
        }

        "drop all elements if predicate always satisfied" {
            dropWhile(xs) { it < 100 } shouldBe List.of()
        }

        "return Nil if input is empty" {
            dropWhile(List.empty<Int>()) { it < 100 } shouldBe Nil
        }
    }

    "List.init" should {
        "return all but the last element" {
            init(xs) shouldBe List.of(1, 2, 3, 4)
        }

        "return Nil if only one element exists" {
            init(List.of(1)) shouldBe Nil
        }

        "throw an exception if no elements exist" {
            shouldThrow<java.lang.IllegalStateException> {
                init(List.empty<Int>())
            }
        }
    }
})


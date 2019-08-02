package chapter3

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.WordSpec

class ExerciseSpec : WordSpec({

    "List.tail" should {
        "return the the tail when present" {
            tail(List.of(1, 2, 3, 4, 5)) shouldBe List.of(2, 3, 4, 5)
        }

        "throw an illegal state exception when no tail is present" {
            shouldThrow<IllegalStateException> {
                tail(Nil)
            }
        }
    }

    "List.setHead" should {
        "return a new List with a replaced head" {
            setHead(List.of(1, 2, 3, 4, 5), 6) shouldBe List.of(6, 2, 3, 4, 5)
        }

        "throw an illegal state exception when no head is present" {
            shouldThrow<IllegalStateException> {
                setHead(Nil, 6)
            }
        }
    }

    "List.drop" should {
        "drop a given number of elements within capacity" {
            drop(List.of(1, 2, 3, 4, 5), 3) shouldBe List.of(4, 5)
        }

        "drop a given number of elements up to capacity" {
            drop(List.of(1, 2, 3, 4, 5), 5) shouldBe Nil
        }

        "throw an illegal state exception when dropped elements exceed capacity" {
            shouldThrow<IllegalStateException> {
                drop(List.of(1, 2, 3, 4, 5), 6)
            }
        }
    }

    "List.dropWhile" should {
        "drop elements until predicate is no longer satisfied" {
            dropWhile(List.of(1, 2, 3, 4, 5)) { it < 4 } shouldBe List.of(4, 5)
        }

        "drop no elements if predicate never satisfied" {
            dropWhile(List.of(1, 2, 3, 4, 5)) { it == 100 } shouldBe List.of(1, 2, 3, 4, 5)
        }

        "drop all elements if predicate always satisfied" {
            dropWhile(List.of(1, 2, 3, 4, 5)) { it < 100 } shouldBe List.of()
        }

        "return Nil if input is empty" {
            dropWhile(List.empty<Int>()) { it < 100 } shouldBe Nil
        }
    }

    "List.init" should {
        "return all but the last element" {
            init(List.of(1, 2, 3, 4, 5)) shouldBe List.of(1, 2, 3, 4)
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

    "List.length" should {
        "calculate the length" {
            length(List.of(1, 2, 3, 4, 5)) shouldBe 5
        }

        "calculate zero for an empty list" {
            length(Nil) shouldBe 0
        }
    }

    "List.foldLeft" should {
        "apply a function f providing a zero accumulator from tail recursive position" {
            foldLeft(List.of(1, 2, 3, 4, 5), 0, { x, y -> x + y }) shouldBe 15
        }
    }

    "List.sumL" should {
        "add all integers" {
            sumL(List.of(1, 2, 3, 4, 5)) shouldBe 15
        }
    }

    "List.productL" should {
        "multiply all doubles" {
            productL(List.of(1.0, 2.0, 3.0, 4.0, 5.0)) shouldBe 120.0
        }
    }

    "List.lengthL" should {
        "count the list elements" {
            lengthL(List.of(1, 2, 3, 4, 5)) shouldBe 5
        }
    }

    "List.reverse" should {
        "reverse list elements" {
            reverse(List.of(1, 2, 3, 4, 5)) shouldBe List.of(5, 4, 3, 2, 1)
        }
    }

    "List.foldLeftR" should {
        "implement foldLeft functionality using foldRight" {
            foldLeftR(List.of(1, 2, 3, 4, 5), 0, { x, y -> x + y }) shouldBe 15
        }
    }

    "List.foldRightL" should {
        "implement foldRight functionality using foldLeft" {
            foldRightL(List.of(1, 2, 3, 4, 5), 0, { x, y -> x + y }) shouldBe 15
        }
    }

    "List.append" should {
        "append two lists to each other" {
            appendR(List.of(1, 2, 3), List.of(4, 5, 6)) shouldBe List.of(1, 2, 3, 4, 5, 6)
        }
    }

    "List.concat" should {
        "concatenate a list of lists into a single list" {
            concat(List.of(List.of(1, 2, 3), List.of(4, 5, 6))) shouldBe List.of(1, 2, 3, 4, 5, 6)
        }
    }
})


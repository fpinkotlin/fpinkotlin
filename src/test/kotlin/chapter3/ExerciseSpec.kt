package chapter3

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.WordSpec

class ExerciseSpec : WordSpec({

    "list tail" should {
        "return the the tail when present" {
            tail(List.of(1, 2, 3, 4, 5)) shouldBe List.of(2, 3, 4, 5)
        }

        "throw an illegal state exception when no tail is present" {
            shouldThrow<IllegalStateException> {
                tail(Nil)
            }
        }
    }

    "list setHead" should {
        "return a new List with a replaced head" {
            setHead(List.of(1, 2, 3, 4, 5), 6) shouldBe List.of(6, 2, 3, 4, 5)
        }

        "throw an illegal state exception when no head is present" {
            shouldThrow<IllegalStateException> {
                setHead(Nil, 6)
            }
        }
    }

    "list drop" should {
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

    "list dropWhile" should {
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

    "list init" should {
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

    "list length" should {
        "calculate the length" {
            length(List.of(1, 2, 3, 4, 5)) shouldBe 5
        }

        "calculate zero for an empty list" {
            length(Nil) shouldBe 0
        }
    }

    "list foldLeft" should {
        "apply a function f providing a zero accumulator from tail recursive position" {
            foldLeft(List.of(1, 2, 3, 4, 5), 0, { x, y -> x + y }) shouldBe 15
        }
    }

    "list sumL" should {
        "add all integers" {
            sumL(List.of(1, 2, 3, 4, 5)) shouldBe 15
        }
    }

    "list productL" should {
        "multiply all doubles" {
            productL(List.of(1.0, 2.0, 3.0, 4.0, 5.0)) shouldBe 120.0
        }
    }

    "list lengthL" should {
        "count the list elements" {
            lengthL(List.of(1, 2, 3, 4, 5)) shouldBe 5
        }
    }

    "list reverse" should {
        "reverse list elements" {
            reverse(List.of(1, 2, 3, 4, 5)) shouldBe List.of(5, 4, 3, 2, 1)
        }
    }

    "list foldLeftR" should {
        "implement foldLeft functionality using foldRight" {
            foldLeftR(List.of(1, 2, 3, 4, 5), 0, { x, y -> x + y }) shouldBe 15
        }
    }

    "list foldRightL" should {
        "implement foldRight functionality using foldLeft" {
            foldRightL(List.of(1, 2, 3, 4, 5), 0, { x, y -> x + y }) shouldBe 15
        }
    }

    "list append" should {
        "append two lists to each other" {
            append(List.of(1, 2, 3), List.of(4, 5, 6)) shouldBe List.of(1, 2, 3, 4, 5, 6)
        }
    }

    "list concat" should {
        "concatenate a list of lists into a single list" {
            concat(List.of(List.of(1, 2, 3), List.of(4, 5, 6))) shouldBe List.of(1, 2, 3, 4, 5, 6)
            concat2(List.of(List.of(1, 2, 3), List.of(4, 5, 6))) shouldBe List.of(1, 2, 3, 4, 5, 6)
        }
    }

    "list increment" should {
        "add 1 to every element" {
            increment(List.of(1, 2, 3, 4, 5)) shouldBe List.of(2, 3, 4, 5, 6)
        }
    }

    "list doubleToString" should {
        "convert every double element to a string" {
            doubleToString(List.of(1.1, 1.2, 1.3, 1.4)) shouldBe List.of("1.1", "1.2", "1.3", "1.4")
        }
    }

    "list map" should {
        "apply a function to every list element" {
            map(List.of(1, 2, 3, 4, 5), { it * 10 }) shouldBe List.of(10, 20, 30, 40, 50)
        }
    }

    "list filter" should {
        "filter out elements not compliant to predicate" {
            filter(List.of(1, 2, 3, 4, 5), { it % 2 == 0 }) shouldBe List.of(2, 4)
            filter2(List.of(1, 2, 3, 4, 5), { it % 2 == 0 }) shouldBe List.of(2, 4)
        }
    }

    "list flatmap" should {
        "map and flatten a list" {
            flatMap(List.of(1, 2, 3)) { i -> List.of(i, i) } shouldBe List.of(1, 1, 2, 2, 3, 3)
            flatMap2(List.of(1, 2, 3)) { i -> List.of(i, i) } shouldBe List.of(1, 1, 2, 2, 3, 3)
        }
    }

    "list add" should {
        "add elements of two corresponding lists" {
            add(List.of(1, 2, 3), List.of(4, 5, 6)) shouldBe List.of(5, 7, 9)
        }
    }

    "list zipWith" should {
        "apply a function to elements of two corresponding lists" {
            zipWith(List.of(1, 2, 3), List.of(4, 5, 6)) { x, y -> x + y } shouldBe List.of(5, 7, 9)
        }
    }

    "list subsequence" should {
        "determine if a list starts with" {
            startsWith(List.of(1, 2, 3), List.of(1)) shouldBe true
            startsWith(List.of(1, 2, 3), List.of(1, 2)) shouldBe true
            startsWith(List.of(1, 2, 3), List.of(1, 2, 3)) shouldBe true
            startsWith(List.of(1, 2, 3), List.of(2, 3)) shouldBe false
            startsWith(List.of(1, 2, 3), List.of(3)) shouldBe false
            startsWith(List.of(1, 2, 3), List.of(6)) shouldBe false
        }

        "identify subsequences of a list" {
            hasSubsequence(List.of(1, 2, 3, 4, 5), List.of(1)) shouldBe true
            hasSubsequence(List.of(1, 2, 3, 4, 5), List.of(1, 2)) shouldBe true
            hasSubsequence(List.of(1, 2, 3, 4, 5), List.of(2, 3)) shouldBe true
            hasSubsequence(List.of(1, 2, 3, 4, 5), List.of(3, 4)) shouldBe true
            hasSubsequence(List.of(1, 2, 3, 4, 5), List.of(3, 4, 5)) shouldBe true
            hasSubsequence(List.of(1, 2, 3, 4, 5), List.of(4)) shouldBe true

            hasSubsequence(List.of(1, 2, 3, 4, 5), List.of(1, 4)) shouldBe false
            hasSubsequence(List.of(1, 2, 3, 4, 5), List.of(1, 3)) shouldBe false
            hasSubsequence(List.of(1, 2, 3, 4, 5), List.of(2, 4)) shouldBe false
        }
    }

    "tree size" should {
        "determine the total size of a tree" {
            val tree = Branch(Branch(Leaf(1), Leaf(2)), Branch(Leaf(3), Leaf(4)))
            size(tree) shouldBe 7
        }
    }

    "tree maximum" should {
        "determine the maximum value held in a tree" {
            val tree = Branch(Branch(Leaf(1), Leaf(9)), Branch(Leaf(3), Leaf(4)))
            maximum(tree) shouldBe 9
        }
    }

    "tree depth" should {
        "determine the maximum depth from the root to any leaf" {
            val tree = Branch( //0
                    Branch(Leaf(1), Leaf(2)), //2
                    Branch(Leaf(3), //2
                            Branch(Branch(Leaf(4), Leaf(5)), //4
                                    Branch(Leaf(6), //4
                                            Branch(Leaf(7), Leaf(8)))))) //5
            depth(tree) shouldBe 5
        }

    }

    "tree map" should {
        "transform all leaves of a map" {
            val actual = Branch(Branch(Leaf(1), Leaf(2)), Branch(Leaf(3), Leaf(4)))
            val expected = Branch(Branch(Leaf(10), Leaf(20)), Branch(Leaf(30), Leaf(40)))
            map(actual) { it * 10 } shouldBe expected
        }
    }

    "tree fold" should {

        val tree = Branch( //0
                Branch(Leaf(1), Leaf(2)), //2
                Branch(Leaf(3), //2
                        Branch(Branch(Leaf(4), Leaf(5)), //4
                                Branch(Leaf(21), //4
                                        Branch(Leaf(7), Leaf(8)))))) //5
        "generalise size" {
            fold(tree, { 1 }, { b1: Int, b2: Int -> 1 + b1 + b2 }) shouldBe 15
        }

        "generalise maximum" {
            fold(tree, { a -> a }, { b1: Int, b2: Int -> maxOf(b1, b2) }) shouldBe 21
        }

        "generalise depth" {
            fold(tree, { _ -> 0 }, { b1: Int, b2: Int -> 1 + maxOf(b1, b2) }) shouldBe 5
        }

        "generalise map" {
            fold(tree, { Leaf(it * 10) }, { b1: Tree<Int>, b2: Tree<Int> -> Branch(b1, b2) }) shouldBe
                    Branch( //0
                            Branch(Leaf(10), Leaf(20)), //2
                            Branch(Leaf(30), //2
                                    Branch(Branch(Leaf(40), Leaf(50)), //4
                                            Branch(Leaf(210), //4
                                                    Branch(Leaf(70), Leaf(80))))))
        }
    }
})

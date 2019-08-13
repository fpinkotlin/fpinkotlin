package chapter3.exercises

import chapter3.listings.Branch
import chapter3.listings.Leaf
import chapter3.listings.List
import chapter3.listings.Nil
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.WordSpec

class ExerciseSpec : WordSpec({

    "list tail" should {
        "return the the tail when present" {
            Solution_3_1.tail(List.of(1, 2, 3, 4, 5)) shouldBe List.of(2, 3, 4, 5)
        }

        "throw an illegal state exception when no tail is present" {
            shouldThrow<IllegalStateException> {
                Solution_3_1.tail(Nil)
            }
        }
    }

    "list setHead" should {
        "return a new List with a replaced head" {
            Solution_3_2.setHead(List.of(1, 2, 3, 4, 5), 6) shouldBe List.of(6, 2, 3, 4, 5)
        }

        "throw an illegal state exception when no head is present" {
            shouldThrow<IllegalStateException> {
                Solution_3_2.setHead(Nil, 6)
            }
        }
    }

    "list drop" should {
        "drop a given number of elements within capacity" {
            Solution_3_3.drop(List.of(1, 2, 3, 4, 5), 3) shouldBe List.of(4, 5)
        }

        "drop a given number of elements up to capacity" {
            Solution_3_3.drop(List.of(1, 2, 3, 4, 5), 5) shouldBe Nil
        }

        "throw an illegal state exception when dropped elements exceed capacity" {
            shouldThrow<IllegalStateException> {
                Solution_3_3.drop(List.of(1, 2, 3, 4, 5), 6)
            }
        }
    }

    "list dropWhile" should {
        "drop elements until predicate is no longer satisfied" {
            Solution_3_4.dropWhile(List.of(1, 2, 3, 4, 5)) { it < 4 } shouldBe List.of(4, 5)
        }

        "drop no elements if predicate never satisfied" {
            Solution_3_4.dropWhile(List.of(1, 2, 3, 4, 5)) { it == 100 } shouldBe List.of(1, 2, 3, 4, 5)
        }

        "drop all elements if predicate always satisfied" {
            Solution_3_4.dropWhile(List.of(1, 2, 3, 4, 5)) { it < 100 } shouldBe List.of()
        }

        "return Nil if input is empty" {
            Solution_3_4.dropWhile(List.empty<Int>()) { it < 100 } shouldBe Nil
        }
    }

    "list init" should {
        "return all but the last element" {
            Solution_3_5.init(List.of(1, 2, 3, 4, 5)) shouldBe List.of(1, 2, 3, 4)
        }

        "return Nil if only one element exists" {
            Solution_3_5.init(List.of(1)) shouldBe Nil
        }

        "throw an exception if no elements exist" {
            shouldThrow<java.lang.IllegalStateException> {
                Solution_3_5.init(List.empty<Int>())
            }
        }
    }

    "list length" should {
        "calculate the length" {
            Solution_3_8.length(List.of(1, 2, 3, 4, 5)) shouldBe 5
        }

        "calculate zero for an empty list" {
            Solution_3_8.length(Nil) shouldBe 0
        }
    }

    "list foldLeft" should {
        "apply a function f providing a zero accumulator from tail recursive position" {
            Solution_3_9.foldLeft(List.of(1, 2, 3, 4, 5), 0, { x, y -> x + y }) shouldBe 15
        }
    }

    "list sumL" should {
        "add all integers" {
            Solution_3_10.sumL(List.of(1, 2, 3, 4, 5)) shouldBe 15
        }
    }

    "list productL" should {
        "multiply all doubles" {
            Solution_3_10.productL(List.of(1.0, 2.0, 3.0, 4.0, 5.0)) shouldBe 120.0
        }
    }

    "list lengthL" should {
        "count the list elements" {
            Solution_3_10.lengthL(List.of(1, 2, 3, 4, 5)) shouldBe 5
        }
    }

    "list reverse" should {
        "reverse list elements" {
            Solution_3_11.reverse(List.of(1, 2, 3, 4, 5)) shouldBe List.of(5, 4, 3, 2, 1)
        }
    }

    "list foldLeftR" should {
        "implement foldLeft functionality using foldRight" {
            Solution_3_12.foldLeftR(List.of(1, 2, 3, 4, 5), 0, { x, y -> x + y }) shouldBe 15
        }
    }

    "list foldRightL" should {
        "implement foldRight functionality using foldLeft" {
            Solution_3_12.foldRightL(List.of(1, 2, 3, 4, 5), 0, { x, y -> x + y }) shouldBe 15
        }
    }

    "list append" should {
        "append two lists to each other" {
            Solution_3_13.append(List.of(1, 2, 3), List.of(4, 5, 6)) shouldBe List.of(1, 2, 3, 4, 5, 6)
        }
    }

    "list concat" should {
        "concatenate a list of lists into a single list" {
            Solution_3_14.concat(List.of(List.of(1, 2, 3), List.of(4, 5, 6))) shouldBe List.of(1, 2, 3, 4, 5, 6)
            Solution_3_14.concat2(List.of(List.of(1, 2, 3), List.of(4, 5, 6))) shouldBe List.of(1, 2, 3, 4, 5, 6)
        }
    }

    "list increment" should {
        "add 1 to every element" {
            Solution_3_15.increment(List.of(1, 2, 3, 4, 5)) shouldBe List.of(2, 3, 4, 5, 6)
        }
    }

    "list doubleToString" should {
        "convert every double element to a string" {
            Solution_3_16.doubleToString(List.of(1.1, 1.2, 1.3, 1.4)) shouldBe List.of("1.1", "1.2", "1.3", "1.4")
        }
    }

    "list map" should {
        "apply a function to every list element" {
            Solution_3_17.map(List.of(1, 2, 3, 4, 5)) { it * 10 } shouldBe List.of(10, 20, 30, 40, 50)
        }
    }

    "list filter" should {
        "filter out elements not compliant to predicate" {
            Solution_3_18.filter(List.of(1, 2, 3, 4, 5)) { it % 2 == 0 } shouldBe List.of(2, 4)
            Solution_3_20.filter2(List.of(1, 2, 3, 4, 5)) { it % 2 == 0 } shouldBe List.of(2, 4)
        }
    }

    "list flatmap" should {
        "map and flatten a list" {
            Solution_3_19.flatMap(List.of(1, 2, 3)) { i -> List.of(i, i) } shouldBe List.of(1, 1, 2, 2, 3, 3)
            Solution_3_19.flatMap2(List.of(1, 2, 3)) { i -> List.of(i, i) } shouldBe List.of(1, 1, 2, 2, 3, 3)
        }
    }

    "list add" should {
        "add elements of two corresponding lists" {
            Solution_3_21.add(List.of(1, 2, 3), List.of(4, 5, 6)) shouldBe List.of(5, 7, 9)
        }
    }

    "list zipWith" should {
        "apply a function to elements of two corresponding lists" {
            Solution_3_22.zipWith(List.of(1, 2, 3), List.of(4, 5, 6)) { x, y -> x + y } shouldBe List.of(5, 7, 9)
        }
    }

    "list subsequence" should {
        "determine if a list starts with" {
            Solution_3_23.startsWith(List.of(1, 2, 3), List.of(1)) shouldBe true
            Solution_3_23.startsWith(List.of(1, 2, 3), List.of(1, 2)) shouldBe true
            Solution_3_23.startsWith(List.of(1, 2, 3), List.of(1, 2, 3)) shouldBe true
            Solution_3_23.startsWith(List.of(1, 2, 3), List.of(2, 3)) shouldBe false
            Solution_3_23.startsWith(List.of(1, 2, 3), List.of(3)) shouldBe false
            Solution_3_23.startsWith(List.of(1, 2, 3), List.of(6)) shouldBe false
        }

        "identify subsequences of a list" {
            Solution_3_23.hasSubsequence(List.of(1, 2, 3, 4, 5), List.of(1)) shouldBe true
            Solution_3_23.hasSubsequence(List.of(1, 2, 3, 4, 5), List.of(1, 2)) shouldBe true
            Solution_3_23.hasSubsequence(List.of(1, 2, 3, 4, 5), List.of(2, 3)) shouldBe true
            Solution_3_23.hasSubsequence(List.of(1, 2, 3, 4, 5), List.of(3, 4)) shouldBe true
            Solution_3_23.hasSubsequence(List.of(1, 2, 3, 4, 5), List.of(3, 4, 5)) shouldBe true
            Solution_3_23.hasSubsequence(List.of(1, 2, 3, 4, 5), List.of(4)) shouldBe true

            Solution_3_23.hasSubsequence(List.of(1, 2, 3, 4, 5), List.of(1, 4)) shouldBe false
            Solution_3_23.hasSubsequence(List.of(1, 2, 3, 4, 5), List.of(1, 3)) shouldBe false
            Solution_3_23.hasSubsequence(List.of(1, 2, 3, 4, 5), List.of(2, 4)) shouldBe false
        }
    }

    "tree size" should {
        "determine the total size of a tree" {
            val tree = Branch(Branch(Leaf(1), Leaf(2)), Branch(Leaf(3), Leaf(4)))
            Solution_3_24.size(tree) shouldBe 7
        }
    }

    "tree maximum" should {
        "determine the maximum value held in a tree" {
            val tree = Branch(Branch(Leaf(1), Leaf(9)), Branch(Leaf(3), Leaf(4)))
            Solution_3_25.maximum(tree) shouldBe 9
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
            Solution_3_26.depth(tree) shouldBe 5
        }

    }

    "tree map" should {
        "transform all leaves of a map" {
            val actual = Branch(Branch(Leaf(1), Leaf(2)), Branch(Leaf(3), Leaf(4)))
            val expected = Branch(Branch(Leaf(10), Leaf(20)), Branch(Leaf(30), Leaf(40)))
            Solution_3_27.map(actual) { it * 10 } shouldBe expected
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
            Solution_3_28.sizeF(tree) shouldBe 15
        }

        "generalise maximum" {
            Solution_3_28.maximumF(tree) shouldBe 21
        }

        "generalise depth" {
            Solution_3_28.depthF(tree) shouldBe 5
        }

        "generalise map" {
            Solution_3_28.mapF(tree) { it * 10 } shouldBe
                    Branch( //0
                            Branch(Leaf(10), Leaf(20)), //2
                            Branch(Leaf(30), //2
                                    Branch(Branch(Leaf(40), Leaf(50)), //4
                                            Branch(Leaf(210), //4
                                                    Branch(Leaf(70), Leaf(80))))))
        }
    }
})

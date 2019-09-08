package chapter3.solutions

import chapter3.Branch
import chapter3.Leaf
import chapter3.Tree
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

// tag::init[]
fun depth(tree: Tree<Int>): Int =
        when (tree) {
            is Leaf -> 0
            is Branch -> 1 + maxOf(depth(tree.left), depth(tree.right))
        }
// end::init[]

class Solution_3_26 : WordSpec({
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
})
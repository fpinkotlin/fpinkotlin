package chapter3.solutions.sol24

import chapter3.Branch
import chapter3.Leaf
import chapter3.Tree
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

// tag::init[]
fun <A> size(tree: Tree<A>): Int =
    when (tree) {
        is Leaf -> 1
        is Branch -> 1 + size(tree.left) + size(tree.right)
    }
// end::init[]

class Solution24 : WordSpec({
    "tree size" should {
        "determine the total size of a tree" {
            val tree = Branch(
                Branch(Leaf(1), Leaf(2)),
                Branch(Leaf(3), Leaf(4))
            )
            size(tree) shouldBe 7
        }
    }
})

package chapter3.exercises

import chapter3.listings.Branch
import chapter3.listings.Leaf
import chapter3.listings.Tree

object Solution_3_26 {
    // tag::init[]
    fun depth(tree: Tree<Int>): Int =
            when (tree) {
                is Leaf -> 0
                is Branch -> 1 + maxOf(depth(tree.left), depth(tree.right))
            }
    // end::init[]
}
package chapter3.exercises

import chapter3.listings.Branch
import chapter3.listings.Leaf
import chapter3.listings.Tree

object Solution_3_25 {
    // tag::init[]
    fun maximum(tree: Tree<Int>): Int =
            when (tree) {
                is Leaf -> tree.value
                is Branch -> maxOf(maximum(tree.left), maximum(tree.right))
            }
    // end::init[]
}
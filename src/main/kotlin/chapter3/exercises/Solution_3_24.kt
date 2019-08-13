package chapter3.exercises

import chapter3.listings.Branch
import chapter3.listings.Leaf
import chapter3.listings.Tree

object Solution_3_24 {
    // tag::init[]
    fun <A> size(tree: Tree<A>): Int =
            when (tree) {
                is Leaf -> 1
                is Branch -> 1 + size(tree.left) + size(tree.right)
            }
    // end::init[]
}
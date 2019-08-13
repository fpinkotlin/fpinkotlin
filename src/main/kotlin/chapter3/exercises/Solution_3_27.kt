package chapter3.exercises

import chapter3.listings.Branch
import chapter3.listings.Leaf
import chapter3.listings.Tree

object Solution_3_27 {
    // tag::init[]
    fun <A, B> map(tree: Tree<A>, f: (A) -> B): Tree<B> =
            when (tree) {
                is Leaf -> Leaf(f(tree.value))
                is Branch -> Branch(map(tree.left, f), map(tree.right, f))
            }
    // end::init[]
}
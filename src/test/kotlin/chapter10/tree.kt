package chapter10

import arrow.Kind

class ForTree private constructor() {
    companion object
}

typealias TreeOf<A> = Kind<ForTree, A>

fun <A> TreeOf<A>.fix() = this as Tree<A>

sealed class Tree<out A> : TreeOf<A>

data class Leaf<A>(val value: A) : Tree<A>()

data class Branch<A>(
    val left: Tree<A>,
    val right: Tree<A>
) : Tree<A>()

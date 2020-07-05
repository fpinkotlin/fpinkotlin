package chapter10

import arrow.higherkind

@higherkind
sealed class Tree<out A> : TreeOf<A>

data class Leaf<A>(val value: A) : Tree<A>()

data class Branch<A>(
    val left: Tree<A>,
    val right: Tree<A>
) : Tree<A>()

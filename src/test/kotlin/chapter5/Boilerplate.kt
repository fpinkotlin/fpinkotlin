package chapter5

import chapter4.Option
import chapter4.solutions.getOrElse
import chapter4.solutions.map

object Boilerplate {
    fun <A, B> Stream<A>.foldRight(z: () -> B, f: (A, () -> B) -> B): B =
        when (this) {
            is Cons -> f(this.head()) { this.tail().foldRight(z, f) }
            is Empty -> z()
        }

    fun <A> Option<A>.isEmpty(): Boolean = this
        .map { false }
        .getOrElse { true }
}

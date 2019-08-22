package chapter4

import chapter3.exercises.Solution_3_12
import chapter3.listings.Cons
import chapter3.listings.List
import chapter3.listings.Nil

fun <A, B> List<A>.map(f: (A) -> B): List<B> = Solution_3_12.foldRightL(this, List.empty<B>(), { a, b -> Cons(f(a), b) })

fun <A, B> List<A>.foldRight(z: B, f: (A, B) -> B): B =
        when (this) {
            is Nil -> z
            is Cons -> f(this.head, this.tail.foldRight(z, f))
        }

fun List<Double>.sum(): Double = Solution_3_12.foldRightL(this, 0.0, { a, b -> a + b })

fun <A> List<A>.size(): Int = Solution_3_12.foldRightL(this, 0, { _, acc -> 1 + acc })

fun List<Double>.isEmpty(): Boolean = when(this) {
    is Nil -> true
    is Cons -> false
}

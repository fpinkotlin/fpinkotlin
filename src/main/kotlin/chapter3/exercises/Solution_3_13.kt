package chapter3.exercises

import chapter3.exercises.Solution_3_7.foldRight
import chapter3.listings.Cons
import chapter3.listings.List

object Solution_3_13 {
    // tag::init[]
    fun <A> append(a1: List<A>, a2: List<A>): List<A> =
            foldRight(a1, a2, { x, y -> Cons(x, y) })
    // end::init[]
}
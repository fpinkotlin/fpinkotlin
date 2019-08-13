package chapter3.exercises

import chapter3.exercises.Solution_3_7.foldRight
import chapter3.listings.Cons
import chapter3.listings.List

object Solution_3_18 {
    // tag::init[]
    fun <A> filter(xs: List<A>, f: (A) -> Boolean): List<A> =
            foldRight(xs, List.empty(), { a, ls -> if (f(a)) Cons(a, ls) else ls })
    // end::init[]
}
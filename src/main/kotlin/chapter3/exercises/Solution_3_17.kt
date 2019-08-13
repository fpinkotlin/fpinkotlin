package chapter3.exercises

import chapter3.exercises.Solution_3_12.foldRightL
import chapter3.listings.Cons
import chapter3.listings.List

object Solution_3_17 {
    // tag::init[]
    fun <A, B> map(xs: List<A>, f: (A) -> B): List<B> =
            foldRightL(xs, List.empty(), { a, xa -> Cons(f(a), xa) })
    // end::init[]
}
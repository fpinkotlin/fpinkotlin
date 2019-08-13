package chapter3.exercises

import chapter3.listings.Cons
import chapter3.listings.List

object Solution_3_15 {
    // tag::init[]
    fun increment(xs: List<Int>): List<Int> =
            Solution_3_7.foldRight(xs, List.empty(), { i: Int, ls -> Cons(i + 1, ls) })
    // end::init[]
}
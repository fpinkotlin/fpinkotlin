package chapter3.exercises

import chapter3.exercises.Solution_3_7.foldRight
import chapter3.listings.List

object Solution_3_8 {
    // tag::exercise3.8[]
    fun <A> length(xs: List<A>): Int =
            foldRight(xs, 0, { _, acc -> 1 + acc })
    // end::exercise3.8[]
}
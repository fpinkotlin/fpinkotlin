package chapter3.exercises

import chapter3.exercises.Solution_3_7.foldRight
import chapter3.listings.Cons
import chapter3.listings.List

object Solution_3_16 {
    // tag::init[]
    fun doubleToString(xs: List<Double>): List<String> =
            foldRight(xs, List.empty(), { d, ds -> Cons(d.toString(), ds) })
    // end::init[]
}
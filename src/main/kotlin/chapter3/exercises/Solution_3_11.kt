package chapter3.exercises

import chapter3.exercises.Solution_3_9.foldLeft
import chapter3.listings.Cons
import chapter3.listings.List

object Solution_3_11 {
    // tag::init[]
    fun <A> reverse(xs: List<A>): List<A> =
            foldLeft(xs, List.empty(), { t: List<A>, h: A -> Cons(h, t) })

    fun <A> reverse2(xs: List<A>): List<A> =
            foldLeft(xs, List.empty(), { t: List<A>, h: A -> Cons(h, t) })
    // end::init[]
}
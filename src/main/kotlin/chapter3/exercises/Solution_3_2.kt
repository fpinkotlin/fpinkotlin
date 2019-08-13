package chapter3.exercises

import chapter3.listings.Cons
import chapter3.listings.List
import chapter3.listings.Nil

object Solution_3_2 {
    // tag::init[]
    fun <A> setHead(xs: List<A>, x: A): List<A> =
            when (xs) {
                is Cons -> Cons(x, xs.tail)
                is Nil -> throw IllegalStateException("Cannot replace `head` of a Nil list")
            }
    // end::init[]
}
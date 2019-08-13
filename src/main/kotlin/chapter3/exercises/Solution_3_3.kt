package chapter3.exercises

import chapter3.listings.Cons
import chapter3.listings.List
import chapter3.listings.Nil

object Solution_3_3 {
    // tag::init[]
    fun <A> drop(l: List<A>, n: Int): List<A> =
            if (n == 0) l
            else when (l) {
                is Cons -> drop(l.tail, n - 1)
                is Nil -> throw IllegalStateException("Cannot drop more elements than in list")
            }
    // end::init[]
}
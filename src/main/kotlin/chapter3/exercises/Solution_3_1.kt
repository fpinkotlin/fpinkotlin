package chapter3.exercises

import chapter3.listings.Cons
import chapter3.listings.List
import chapter3.listings.Nil

object Solution_3_1 {
    // tag::init[]
    fun <A> tail(xs: List<A>): List<A> =
            when (xs) {
                is Cons -> xs.tail
                is Nil -> throw IllegalStateException("Nil cannot have a `tail`")
            }
    // end::init[]
}

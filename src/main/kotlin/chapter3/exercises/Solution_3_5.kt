package chapter3.exercises

import chapter3.listings.Cons
import chapter3.listings.List
import chapter3.listings.Nil

object Solution_3_5 {
    // tag::init[]
    fun <A> init(l: List<A>): List<A> =
            when (l) {
                is Cons ->
                    if (l.tail == Nil) Nil
                    else Cons(l.head, init(l.tail))
                is Nil ->
                    throw IllegalStateException("Cannot init Nil list")
            }
    // end::init[]
}
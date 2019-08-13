package chapter3.exercises

import chapter3.listings.Cons
import chapter3.listings.List
import chapter3.listings.Nil

object Solution_3_4 {
    // tag::init[]
    fun <A> dropWhile(l: List<A>, f: (A) -> Boolean): List<A> =
            when (l) {
                is Cons -> if (f(l.head)) dropWhile(l.tail, f) else l
                is Nil -> l
            }
    // end::init[]
}
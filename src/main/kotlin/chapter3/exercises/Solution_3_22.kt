package chapter3.exercises

import chapter3.listings.Cons
import chapter3.listings.List
import chapter3.listings.Nil

object Solution_3_22 {
    // tag::init[]
    fun <A> zipWith(xa: List<A>, xb: List<A>, f: (A, A) -> A): List<A> =
            when (xa) {
                is Nil -> Nil
                is Cons -> when (xb) {
                    is Nil -> Nil
                    is Cons -> Cons(f(xa.head, xb.head), zipWith(xa.tail, xb.tail, f))
                }
            }
    // end::init[]
}
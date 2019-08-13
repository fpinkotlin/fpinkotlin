package chapter3.exercises

import chapter3.listings.Cons
import chapter3.listings.List
import chapter3.listings.Nil

object Solution_3_21 {
    // tag::init[]
    fun add(xa: List<Int>, xb: List<Int>): List<Int> =
            when (xa) {
                is Nil -> Nil
                is Cons -> when (xb) {
                    is Nil -> Nil
                    is Cons -> Cons(xa.head + xb.head, add(xa.tail, xb.tail))
                }
            }
    // end::init[]
}
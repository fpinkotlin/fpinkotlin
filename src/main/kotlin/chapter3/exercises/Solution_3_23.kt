package chapter3.exercises

import chapter3.listings.Cons
import chapter3.listings.List
import chapter3.listings.Nil

object Solution_3_23 {
    // tag::init[]
    tailrec fun <A> startsWith(l1: List<A>, l2: List<A>): Boolean =
            when (l1) {
                is Nil -> l2 == Nil
                is Cons -> when (l2) {
                    is Nil -> true
                    is Cons ->
                        if (l1.head == l2.head) startsWith(l1.tail, l2.tail)
                        else false
                }
            }

    tailrec fun <A> hasSubsequence(xs: List<A>, sub: List<A>): Boolean =
            when (xs) {
                is Nil -> false
                is Cons ->
                    if (startsWith(xs, sub)) true
                    else hasSubsequence(xs.tail, sub)

            }
    // end::init[]
}
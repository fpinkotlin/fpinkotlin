package chapter3.exercises

import chapter3.listings.Cons
import chapter3.listings.List
import chapter3.listings.Nil

object Solution_3_9 {
    // tag::init[]
    tailrec fun <A, B> foldLeft(xs: List<A>, z: B, f: (B, A) -> B): B =
            when (xs) {
                is Nil -> z
                is Cons -> foldLeft(xs.tail, f(z, xs.head), f)
            }
    // end::init[]
}
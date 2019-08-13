package chapter3.exercises

import chapter3.exercises.Solution_3_19.flatMap
import chapter3.listings.List

object Solution_3_20 {
    // tag::init[]
    fun <A> filter2(xa: List<A>, f: (A) -> Boolean): List<A> =
            flatMap(xa) { a ->
                if (f(a)) List.of(a) else List.empty()
            }
    // end::init[]
}
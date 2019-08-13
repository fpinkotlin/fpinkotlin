package chapter3.exercises

import chapter3.exercises.Solution_3_13.append
import chapter3.exercises.Solution_3_7.foldRight
import chapter3.listings.Cons
import chapter3.listings.List

object Solution_3_14 {
    // tag::init[]
    fun <A> concat(xxs: List<List<A>>): List<A> =
            foldRight(xxs, List.empty(), { xs1: List<A>, xs2: List<A> ->
                foldRight(xs1, xs2, { a, ls -> Cons(a, ls) })
            })

    fun <A> concat2(xxs: List<List<A>>): List<A> =
            foldRight(xxs, List.empty(), { xs1, xs2 -> append(xs1, xs2) })
    // end::init[]
}
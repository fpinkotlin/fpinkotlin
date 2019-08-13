package chapter3.exercises

import chapter3.exercises.Solution_3_13.append
import chapter3.exercises.Solution_3_7.foldRight
import chapter3.listings.Cons
import chapter3.listings.List

object Solution_3_19 {
    // tag::init[]
    fun <A, B> flatMap(xa: List<A>, f: (A) -> List<B>): List<B> =
            foldRight(xa, List.empty(), { a, lb -> append(f(a), lb) })

    fun <A, B> flatMap2(xa: List<A>, f: (A) -> List<B>): List<B> =
            foldRight(xa, List.empty(), { a, xb ->
                foldRight(f(a), xb, { b, lb -> Cons(b, lb) })
            })
    // end::init[]
}
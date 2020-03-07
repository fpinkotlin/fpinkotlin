package chapter3.exercises

import chapter3.Cons
import chapter3.List
import chapter3.Nil
import chapter3.exercises.Exercise_3_7.foldRight

object Exercise_3_7 {
    fun <A, B> foldRight(xs: List<A>, z: B, f: (A, B) -> B): B =
        when (xs) {
            is Nil -> z
            is Cons -> f(xs.head, foldRight(xs.tail, z, f))
        }

    val f = { x: Int, y: List<Int> -> Cons(x, y) }
    val z = Nil as List<Int>

    val trace = {
        foldRight(List.of(1, 2, 3), z, f)
        Cons(
            1,
            foldRight(List.of(2, 3), z, f)
        )
        Cons(
            1, Cons(
                2,
                foldRight(List.of(3), z, f)
            )
        )
        Cons(
            1, Cons(
                2, Cons(
                    3,
                    foldRight(List.empty(), z, f)
                )
            )
        )
        Cons(
            1, Cons(
                2, Cons(
                    3,
                    Nil
                )
            )
        )
    }
}

fun main() {
    // tag::init[]
    foldRight(
        List.of(1, 2, 3),
        List.empty<Int>(),
        { x, y -> Cons(x, y) })
    // end::init[]
}

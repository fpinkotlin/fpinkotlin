package chapter3

import chapter3.Listing_3_11.foldRight

object Listing_3_11 {

    //tag::init[]
    fun <A, B> foldRight(xs: List<A>, z: B, f: (A, B) -> B): B =
        when (xs) {
            is Nil -> z
            is Cons -> f(xs.head, foldRight(xs.tail, z, f))
        }

    fun sum2(ints: List<Int>): Int =
        foldRight(ints, 0, { a, b -> a + b })

    fun product2(dbs: List<Double>): Double =
        foldRight(dbs, 1.0, { a, b -> a * b })
    //end::init[]
}

fun main() {
    //tag::substitution1[]
    //tag::substitution2[]
    foldRight(Cons(1, Cons(2, Cons(3, Nil))),
        0, { x, y -> x + y })
    //end::substitution2[]
    1 + foldRight(Cons(2, Cons(3, Nil)), 0,
        { x, y -> x + y })
    1 + (2 + foldRight(Cons(3, Nil), 0,
        { x, y -> x + y }))
    1 + (2 + (3 + (foldRight(Nil as List<Int>, 0,
        { x, y -> x + y }))))
    1 + (2 + (3 + (0)))
    6
    //end::substitution1[]
}

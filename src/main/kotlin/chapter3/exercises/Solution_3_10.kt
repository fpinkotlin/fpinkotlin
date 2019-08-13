package chapter3.exercises

import chapter3.exercises.Solution_3_9.foldLeft
import chapter3.listings.List

object Solution_3_10 {
    // tag::init[]
    fun sumL(xs: List<Int>): Int =
            foldLeft(xs, 0, { x, y -> x + y })

    fun productL(xs: List<Double>): Double =
            foldLeft(xs, 1.0, { x, y -> x * y })

    fun <A> lengthL(xs: List<A>): Int =
            foldLeft(xs, 0, { acc, _ -> acc + 1 })
    // end::init[]
}
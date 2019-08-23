package chapter4

import chapter3.exercises.Solution_3_12.foldRightL
import chapter3.exercises.Solution_3_7
import chapter3.listings.Cons
import chapter3.listings.List
import chapter3.listings.Nil
import chapter3.exercises.Solution_3_8.length

object Listing_4_2 {

    fun List<Double>.sum(): Double = TODO()

    fun List<Double>.isEmpty(): Boolean = TODO()

    fun <A> List<A>.size(): Int = TODO()

    fun mean(xs: List<Double>): Double =
            if (xs.isEmpty())
                throw ArithmeticException("mean of emtpy list!")
            else xs.sum() / length(xs)

    fun mean2(xs: List<Double>, onEmpty: Double) =
            if (xs.isEmpty()) onEmpty
            else xs.sum() / xs.size()

    fun mean3(xs: List<Double>): Option<Double> =
            if (xs.isEmpty()) None
            else Some(xs.sum() / xs.size())
}

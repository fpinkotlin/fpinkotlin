package chapter4

import chapter3.List

object Listing_4_2 {

    private fun <A> length(xs: List<A>): Int = TODO()

    private fun List<Double>.sum(): Double = TODO()

    private fun List<Double>.isEmpty(): Boolean = TODO()

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

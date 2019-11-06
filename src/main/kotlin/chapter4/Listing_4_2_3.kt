package chapter4

import chapter3.List

object Listing_4_2_3 {

    private fun <A> length(xs: List<A>): Int = TODO()

    private fun List<Double>.sum(): Double = TODO()

    private fun List<Double>.isEmpty(): Boolean = TODO()

    fun <A> List<A>.size(): Int = TODO()

    //tag::init[]
    fun mean(xs: List<Double>): Option<Double> =
        if (xs.isEmpty()) None // <1>
        else Some(xs.sum() / xs.size()) //<2>
    //end::init[]
}

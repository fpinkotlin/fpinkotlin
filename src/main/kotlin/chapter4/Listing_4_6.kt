package chapter4

import chapter3.List

object Listing_4_6 {

    fun List<Double>.sum(): Double = TODO()

    fun <A> List<A>.isEmpty(): Boolean = TODO()

    fun <A> List<A>.size(): Int = TODO()

    //tag::mean[]
    fun mean(xs: List<Double>): Either<String, Double> =
        if (xs.isEmpty())
            Left("mean of empty list!")
        else Right(xs.sum() / xs.size())
    //end::mean[]

    //tag::safediv[]
    fun safeDiv(x: Int, y: Int): Either<Exception, Int> =
        try {
            Right(x / y)
        } catch (e: Exception) {
            Left(e)
        }
    //end::safediv[]

    //tag::try[]
    fun <A> catches(a: () -> A): Either<Exception, A> =
        try {
            Right(a())
        } catch (e: Exception) {
            Left(e)
        }
    //end::try[]
}

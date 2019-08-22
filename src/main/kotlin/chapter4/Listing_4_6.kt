package chapter4

import chapter3.exercises.Solution_3_12
import chapter3.listings.Cons
import chapter3.listings.List
import chapter3.listings.Nil

object Listing_4_6 {

    fun List<Double>.sum(): Double = Solution_3_12.foldRightL(this, 0.0, { a, b -> a + b })

    fun <A> List<A>.isEmpty(): Boolean = when (this) {
        is Nil -> true
        is Cons -> false
    }

    fun <A> List<A>.size(): Int = Solution_3_12.foldRightL(this, 0, { _, acc -> 1 + acc })

    fun mean(xs: List<Double>): Either<String, Double> =
            if (xs.isEmpty())
                Left("mean of empty list!")
            else
                Right(xs.sum() / xs.size())

    fun safeDiv(x: Int, y: Int): Either<Exception, Int> =
            try {
                Right(x / y)
            } catch (e: Exception) {
                Left(e)
            }

    fun <A> Try(a: () -> A): Either<Exception, A> =
            try {
                Right(a())
            } catch (e: Exception) {
                Left(e)
            }


}
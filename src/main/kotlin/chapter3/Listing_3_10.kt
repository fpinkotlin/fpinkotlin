package chapter3

object Listing_3_10 {

    //tag::init[]
    fun sum(xs: List<Int>): Int = when (xs) {
        is Nil -> 0
        is Cons -> xs.head + sum(xs.tail)
    }

    fun product(xs: List<Double>): Double = when (xs) {
        is Nil -> 1.0
        is Cons -> xs.head * product(xs.tail)
    }
    //end::init[]
}

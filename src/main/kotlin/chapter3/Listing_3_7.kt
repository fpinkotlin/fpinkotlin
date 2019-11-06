package chapter3

object Listing_3_7 {
    //tag::init[]
    fun sum(xs: List<Int>): Int = when (xs) {
        is Nil -> 0
        is Cons -> xs.head + sum(xs.tail) // <1>
    }
    //end::init[]
}

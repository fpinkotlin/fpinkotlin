package chapter3

object Listing_3_4 {

    //tag::init[]
    val ints = List.of(1, 2, 3, 4) // <1>

    fun sum(xs: List<Int>): Int =
        when (xs) {
            is Nil -> 0 // <2>
            is Cons -> xs.head + sum(xs.tail) // <3>
        }

    fun main() = sum(ints) // <4>
    //end::init[]
}

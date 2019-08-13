package chapter3

import chapter3.*

object Snippet5 {
    //tag::init[]
    fun sum1(xs: List<Int>): Int = when(xs) {
        is Nil -> 0
        is Cons -> xs.head + sum1(xs.tail) // <1>
    }
    //end::init[]
}

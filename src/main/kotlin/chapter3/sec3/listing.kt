package chapter3.sec3

import chapter3.List
import chapter3.Cons
import chapter3.Nil

//tag::init1[]
fun <A> append(a1: List<A>, a2: List<A>): List<A> =
    when (a1) {
        is Nil -> a2
        is Cons -> Cons(a1.head, append(a1.tail, a2))
    }
//end::init1[]

package chapter3.sec1

//tag::init[]
sealed class List<out A> // <1>

object Nil : List<Nothing>() // <2>

data class Cons<out A>(val head: A, val tail: List<A>) : List<A>() // <3>
//end::init[]

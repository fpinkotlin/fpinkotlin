package chapter3

//Singly Linked List data structure

//tag::example[]
sealed class List<out A> { // <1>
    // helper functions
    //tag::companion[]
    companion object { // <2>
        //tag::sum[]

        fun sum(ints: List<Int>): Int =
                when (ints) {
                    is Nil -> 0
                    is Cons -> ints.head + sum(ints.tail)
                }
        //end::sum[]
        //tag::product[]

        fun product(doubles: List<Double>): Double =
                when (doubles) {
                    is Nil -> 1.0
                    is Cons ->
                        if (doubles.head == 0.0) 0.0
                        else doubles.head * product(doubles.tail)
                }
        //end::product[]

        //tag::of[]
        fun <A> of(vararg aa: A): List<A> { // <3>
            val tail = aa.sliceArray(1 until aa.size)
            return if (aa.isEmpty()) Nil else Cons(aa[0], of(*tail))
        }
        //end::of[]
        //tag::extra[]

        fun <A> empty(): List<A> = Nil

        fun <A> append(xs1: List<A>, xs2: List<A>): List<A> =
                when (xs1) {
                    is Nil -> xs2
                    is Cons -> Cons(xs1.head, append(xs1.tail, xs2))
                }

        fun sumFR(xs: List<Int>): Int = foldRight(xs, 0, { a, b -> a + b })
        //end::extra[]
    }
    //end::companion[]
}

//tag::impls[]
object Nil : List<Nothing>() // <2>

data class Cons<out A>(val head: A, val tail: List<A>) : List<A>() // <3>
//end::impls[]
//end::example[]

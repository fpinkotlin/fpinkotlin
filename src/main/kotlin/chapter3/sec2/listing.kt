package sec2

import kotlin.random.Random

//tag::init1[]
sealed class List<out A> { // <1>

    companion object { // <2>

        fun <A> of(vararg aa: A): List<A> { // <3>
            val tail = aa.sliceArray(1 until aa.size)
            return if (aa.isEmpty()) Nil else Cons(aa[0], of(*tail))
        }

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
    }
}
//end::init1[]

object Nil : List<Nothing>() {
    override fun toString(): String = "Nil"
}

//tag::init7[]
data class Cons<out A>(val head: A, val tail: List<A>) : List<A>()
//end::init7[]

//tag::init2[]
fun <A> of(vararg aa: A): List<A> {
    val tail = aa.sliceArray(1 until aa.size)
    return if (aa.isEmpty()) Nil else Cons(aa[0], List.of(*tail))
}
//end::init2[]

//tag::init3[]
val ints = List.of(1, 2, 3, 4) // <1>

fun sum(xs: List<Int>): Int =
    when (xs) {
        is Nil -> 0 // <2>
        is Cons -> xs.head + sum(xs.tail) // <3>
    }

fun main() = sum(ints) // <4>
//end::init3[]

val listing35 = {
    //tag::init4[]
    val x = Random.nextInt(-10, 10)
    val y: String =
        if (x == 0) { // <1>
            "x is zero"
        } else if (x < 0) { // <2>
            "is negative"
        } else { // <3>
            "x is positive"
        }
    //end::init4[]
}

val listing36 = {
    //tag::init5[]
    val x = Random.nextInt(-10, 10)
    val y: String =
        when { // <1>
            x == 0 -> // <2>
                "x is zero"
            x < 0 -> // <2>
                "x is negative"
            else -> // <3>
                "x is positive"
        }
    //end::init5[]
}

val listing37 = {
    //tag::init6[]
    fun sum(xs: List<Int>): Int =
        when (xs) {
            is Nil -> 0
            is Cons -> xs.head + sum(xs.tail) // <1>
        }
    //end::init6[]
}

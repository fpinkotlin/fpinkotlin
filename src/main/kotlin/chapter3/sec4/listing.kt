package chapter3.sec4

sealed class List<out A>

object Nil : List<Nothing>() {
    override fun toString(): String = "Nil"
}

data class Cons<out A>(val head: A, val tail: List<A>) : List<A>()

//tag::init1[]
fun sum(xs: List<Int>): Int = when (xs) {
    is Nil -> 0
    is Cons -> xs.head + sum(xs.tail)
}

fun product(xs: List<Double>): Double = when (xs) {
    is Nil -> 1.0
    is Cons -> xs.head * product(xs.tail)
}
//end::init1[]

//tag::init2[]
fun <A, B> foldRight(xs: List<A>, z: B, f: (A, B) -> B): B =
    when (xs) {
        is Nil -> z
        is Cons -> f(xs.head, foldRight(xs.tail, z, f))
    }

fun sum2(ints: List<Int>): Int =
    foldRight(ints, 0, { a, b -> a + b })

fun product2(dbs: List<Double>): Double =
    foldRight(dbs, 1.0, { a, b -> a * b })
//end::init2[]

fun main() {
    //tag::substitution1[]
    //tag::substitution2[]
    foldRight(Cons(1, Cons(2, Cons(3, Nil))),
        0, { x, y -> x + y })
    //end::substitution2[]
    1 + foldRight(Cons(2, Cons(3, Nil)), 0,
        { x, y -> x + y })
    1 + (2 + foldRight(Cons(3, Nil), 0,
        { x, y -> x + y }))
    1 + (2 + (3 + (foldRight(Nil as List<Int>, 0,
        { x, y -> x + y }))))
    1 + (2 + (3 + (0)))
    6
    //end::substitution1[]
}

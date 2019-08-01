package chapter3

//List structures
sealed class List<out A> {
    companion object {
        fun sum(ints: List<Int>): Int =
                when (ints) {
                    is Nil -> 0
                    is Cons -> ints.head + sum(ints.tail)
                }

        fun product(doubles: List<Double>): Double =
                when (doubles) {
                    is Nil -> 1.0
                    is Cons ->
                        if (doubles.head == 0.0) 0.0
                        else doubles.head * product(doubles.tail)
                }

        fun <A> of(vararg aa: A): List<A> {
            val tail = aa.sliceArray(1 until aa.size)
            return if (aa.isEmpty()) Nil else Cons(aa[0], of(*tail))
        }

        fun <A> empty(): List<A> = Nil

        fun <A> append(a1: List<A>, a2: List<A>): List<A> =
                when (a1) {
                    is Nil -> a2
                    is Cons -> Cons(a1.head, append(a1.tail, a2))
                }

        fun <A, B> foldRight(ss: List<A>, z: B, f: (A, B) -> B): B =
                when (ss) {
                    is Nil -> z
                    is Cons -> f(ss.head, foldRight(ss.tail, z, f))
                }

        fun sum2(ints: List<Int>): Int = foldRight(ints, 0, { a, b -> a + b })

        fun product2(dbs: List<Double>): Double = foldRight(dbs, 1.0, { a, b -> a * b })
    }
}

object Nil : List<Nothing>() {
    override fun toString(): String = "Nil"
}

data class Cons<out A>(val head: A, val tail: List<A>) : List<A>()

//Tree structures
sealed class Tree<out A>

data class Leaf<A>(val value: A) : Tree<A>()

data class Branch<A>(val left: Tree<A>, val right: Tree<A>) : Tree<A>()



fun main() {
    val ex1: List<Double> = Nil
    val ex2: List<Int> = Cons(1, Nil)
    val ex3: List<String> = Cons("a", Cons("b", Nil))

    println(List.sum(Cons(1, Cons(2, Cons(3, Nil)))))
    println(List.product(Cons(1.0, Cons(2.0, Cons(3.0, Nil)))))
    println(List.product(Cons(0.0, Cons(2.0, Cons(3.0, Nil)))))
    println(List.of(1, 2, 3, 4, 5, 6))
    println(List.append(List.of(1, 2, 3, 4), List.of(5, 6, 7, 8)))

    List.foldRight(Cons(1, Cons(2, Cons(3, Nil))), 0, { x, y -> x + y })
}



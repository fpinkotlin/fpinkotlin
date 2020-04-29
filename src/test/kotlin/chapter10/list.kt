package chapter10

import arrow.Kind

class ForList private constructor() {
    companion object
}

typealias ListOf<A> = Kind<ForList, A>

fun <A> ListOf<A>.fix() = this as List<A>

sealed class List<out A> : ListOf<A> {

    companion object {

        fun <A> of(vararg aa: A): List<A> {
            val tail = aa.sliceArray(1 until aa.size)
            return if (aa.isEmpty()) Nil else Cons(aa[0], of(*tail))
        }

        fun <A> empty(): List<A> = Nil

        fun <A> append(a1: List<A>, a2: List<A>): List<A> =
            a1.foldRight(a2, { x, y -> Cons(x, y) })
    }

    fun <B> foldLeft(z: B, f: (B, A) -> B): B =
        foldRight(
            { b: B -> b },
            { a, g ->
                { b ->
                    g(f(b, a))
                }
            })(z)

    fun <B> foldRight(z: B, f: (A, B) -> B): B =
        when (this) {
            is Nil -> z
            is Cons -> f(this.head, this.tail.foldRight(z, f))
        }
}

object Nil : List<Nothing>()

data class Cons<out A>(
    val head: A,
    val tail: List<A>
) : List<A>()

fun kotlin.collections.List<Int>.asConsList(): List<Int> =
    List.of(*this.toTypedArray())

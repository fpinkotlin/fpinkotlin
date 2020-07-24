package chapter10

import arrow.Kind
import arrow.higherkind

/*
//tag::init1[]
interface Foldable<F<A>> {
    //some abstract methods
}

object ListFoldable : Foldable<List<A>> {
    //some code
}
//end::init1[]
*/

//tag::init2[]
class ForList private constructor() {
    companion object
}
//end::init2[]

//tag::init3[]
typealias ListOf<A> = Kind<ForList, A>
//end::init3[]

//tag::init4[]
sealed class List<out A> : ListOf<A>
//end::init4[]
{
    companion object {

        fun <A> of(vararg aa: A): List<A> {
            val tail = aa.sliceArray(1 until aa.size)
            return if (aa.isEmpty()) Nil else Cons(aa[0], of(*tail))
        }

        fun <A> empty(): List<A> = Nil

        fun <A> append(a1: List<A>, a2: List<A>): List<A> =
            a1.foldRight(a2, { x, y -> Cons(x, y) })

        fun <A> fill(n: Int, a: A): List<A> =
            when (n) {
                0 -> empty<A>()
                else -> Cons(a, fill(n - 1, a))
            }
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

    fun <B> flatMap(f: (A) -> List<B>): List<B> =
        foldRight(empty(), { a, acc -> append(f(a), acc) })
}

object Nil : List<Nothing>()

data class Cons<out A>(
    val head: A,
    val tail: List<A>
) : List<A>()

fun kotlin.collections.List<Int>.asConsList(): List<Int> =
    List.of(*this.toTypedArray())

//tag::init5[]
object ListFoldable : Foldable<ForList> {
    //tag::foldright[]
    override fun <A, B> foldRight(
        fa: ListOf<A>,
        z: B,
        f: (A, B) -> B
    ): B =
    //end::foldright[]
        //tag::foldrightimpl[]
        fa.fix().foldRight(z, f)
    //end::foldrightimpl[]
}
//end::init5[]

//tag::init6[]
interface Foldable<F> {
    fun <A, B> foldRight(fa: Kind<F, A>, z: B, f: (A, B) -> B): B
}
//end::init6[]

//tag::init7[]
fun <A> ListOf<A>.fix() = this as List<A>
//end::init7[]

//tag::init8[]
@higherkind
sealed class ListHK<out A> {
    fun <B> foldRight(z: B, f: (A, B) -> B): B = TODO()
}
//end::init8[]

//tag::init9[]
object ListHKFoldable : Foldable<ForListHK> {
    override fun <A, B> foldRight(
        fa: ListHKOf<A>,
        z: B,
        f: (A, B) -> B
    ): B = fa.fix().foldRight(z, f)
}
//end::init9[]

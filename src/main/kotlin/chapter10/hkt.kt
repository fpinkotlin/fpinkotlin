package chapter10

import arrow.Kind
import arrow.Kind2
import arrow.higherkind
import chapter11.Monad

/*
//tag::init1[]
interface Foldable<F<A>> {
    //some abstract methods
}

object ListFoldable : Foldable<List<A>> {
    //some method implementations with parametrized A
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

sealed class List<out A> : ListOf<A> {
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

interface FoldableListing1 {
    //tag::init45[]
    fun <A, B> foldRight(fa: Kind<ForList, A>, z: B, f: (A, B) -> B): B
    //end::init45[]
        = TODO()
}

interface FoldableListing2 {
    //tag::init46[]
    fun <A, B> foldRight(fa: ListOf<A>, z: B, f: (A, B) -> B): B
    //end::init46[]
        = TODO()
}

//tag::init5[]
object ListFoldable : Foldable<ForList> {

    //tag::foldright[]
    override fun <A, B> foldRight(
        fa: ListOf<A>,
        z: B,
        f: (A, B) -> B
    ): B =
        fa.fix().foldRight(z, f)
    //end::foldright[]
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
sealed class ListK<out A> : ListKOf<A> {
    fun <B> foldRight(z: B, f: (A, B) -> B): B = TODO()
}
//end::init8[]

//tag::init9[]
object ListKFoldable : Foldable<ForListK> {
    override fun <A, B> foldRight(
        fa: ListKOf<A>,
        z: B,
        f: (A, B) -> B
    ): B = fa.fix().foldRight(z, f)
}
//end::init9[]

data class State<S, out A>(val run: (S) -> Pair<A, S>) : StateOf<S, A> {
    fun <B> flatMap(f: (A) -> State<S, B>): State<S, B> = TODO()
}

fun <S, A> StateOf<S, A>.fix() = this as State<S, A>

//tag::init10[]
sealed class ForState private constructor() {
    companion object
}

typealias StateOf<S, A> = Kind2<ForState, S, A>
//end::init10[]

//tag::init11[]
typealias StatePartialOf<S> = Kind<ForState, S>
//end::init11[]

//tag::init12[]
interface StateMonad<S> : Monad<StatePartialOf<S>> { // <1>

    override fun <A> unit(a: A): StateOf<S, A> = // <2>
        State { s -> a to s }

    override fun <A, B> flatMap(
        fa: StateOf<S, A>, // <2>
        f: (A) -> StateOf<S, B>
    ): StateOf<S, B> =
        fa.fix().flatMap { a -> f(a).fix() } // <3>
}
//end::init12[]

//tag::init13[]
val intStateMonad: StateMonad<Int> = object : StateMonad<Int> {}

val stringStateMonad: StateMonad<String> = object : StateMonad<String> {}
//end::init13[]

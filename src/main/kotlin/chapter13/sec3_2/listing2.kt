package chapter13.sec3_2

import arrow.Kind

class ForTailrec {
    companion object
}

typealias TailrecOf<A> = Kind<ForTailrec, A>

inline fun <A> TailrecOf<A>.fix(): Tailrec<A> = this as Tailrec<A>

//tag::init1[]
sealed class Tailrec<A> : TailrecOf<A> {
    //tag::ignore[]
    companion object {
        fun <A> unit(a: A) = Suspend { a }
    }
    //end::ignore[]
    fun <B> flatMap(f: (A) -> Tailrec<B>): Tailrec<B> = FlatMap(this, f)
    fun <B> map(f: (A) -> B): Tailrec<B> = flatMap { a -> Return(f(a)) }
}

data class Return<A>(val a: A) : Tailrec<A>()
data class Suspend<A>(val resume: () -> A) : Tailrec<A>()
data class FlatMap<A, B>(
    val sub: Tailrec<A>,
    val f: (A) -> Tailrec<B>
) : Tailrec<B>()
//end::init1[]

//tag::init2[]
val f = { x: Int -> Return(x) }
    val g = List(100000) { idx -> f }
        .fold(f) { a: (Int) -> Tailrec<Int>, b: (Int) -> Tailrec<Int> ->
            { x: Int ->
                Suspend { Unit }.flatMap { _ -> a(x).flatMap(b) }
            }
        }
//end::init2[]

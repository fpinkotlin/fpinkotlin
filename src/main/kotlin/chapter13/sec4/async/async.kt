package chapter13.sec4.async

import arrow.Kind
import chapter11.Par

class ForAsync {
    companion object
}

typealias AsyncOf<A> = Kind<ForAsync, A>

inline fun <A> AsyncOf<A>.fix(): Async<A> = this as Async<A>

//tag::init1[]
sealed class Async<A> : AsyncOf<A> {
    //tag::ignore[]
    companion object {
        fun <A> unit(a: A) = Return(a)
    }

    //end::ignore[]
    fun <B> flatMap(f: (A) -> Async<B>): Async<B> =
        FlatMap(this, f)

    fun <B> map(f: (A) -> B): Async<B> =
        flatMap { a -> Return(f(a)) }
}

data class Return<A>(val a: A) : Async<A>()
data class Suspend<A>(val resume: Par<A>) : Async<A>() // <1>
data class FlatMap<A, B>(
    val sub: Async<A>,
    val f: (A) -> Async<B>
) : Async<B>()
//end::init1[]

//tag::init2[]
@Suppress("UNCHECKED_CAST") // <1>
tailrec fun <A> step(async: Async<A>): Async<A> =
    when (async) {
        is FlatMap<*, *> -> {
            val y = async.sub as Async<A> // <1>
            val g = async.f as (A) -> Async<A> // <1>
            when (y) {
                is FlatMap<*, *> -> {
                    val x = y.sub as Async<A> // <1>
                    val f = y.f as (A) -> Async<A> // <1>
                    step(x.flatMap { a -> f(a).flatMap(g) })
                }
                is Return -> step(g(y.a))
                else -> async
            }
        }
        else -> async
    }

@Suppress("UNCHECKED_CAST")
fun <A> run(async: Async<A>): Par<A> =
    when (val stepped = step(async)) {
        is Return -> Par.unit(stepped.a)
        is Suspend -> stepped.resume
        is FlatMap<*, *> -> {
            val x = stepped.sub as Async<A> // <1>
            val f = stepped.f as (A) -> Async<A> // <1>
            when (x) {
                is Suspend -> x.resume.flatMap { a -> run(f(a)) }
                else -> throw RuntimeException(
                    "Impossible, step eliminates such cases"
                )
            }
        }
    }
//end::init2[]

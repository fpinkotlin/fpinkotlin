package chapter13.sec3_1

import arrow.Kind
import arrow.extension
import chapter13.Monad
import chapter13.sec3_1.io.monad.monad

class ForIO {
    companion object
}

typealias IOOf<A> = Kind<ForIO, A>

inline fun <A> IOOf<A>.fix(): IO<A> = this as IO<A>

@extension
interface IOMonad : Monad<ForIO> {

    override fun <A> unit(a: A): IOOf<A> =
        IO.unit(a).fix()

    override fun <A, B> flatMap(
        fa: IOOf<A>,
        f: (A) -> IOOf<B>
    ): IOOf<B> =
        fa.fix().flatMap { a -> f(a).fix() }

    override fun <A, B> map(
        fa: Kind<ForIO, A>,
        f: (A) -> B
    ): Kind<ForIO, B> =
        flatMap(fa.fix()) { a -> unit(f(a)) }

    fun <A, B> forever(fa: IOOf<A>): IOOf<B> {
        val t: IOOf<B> by lazy { forever<A, B>(fa) }
        return fa.fix().flatMap { t.fix() }
    }
}

//tag::init1[]
sealed class IO<A> : IOOf<A> {
    companion object {
        fun <A> unit(a: A) = Suspend { a }
    }

    fun <B> flatMap(f: (A) -> IO<B>): IO<B> = FlatMap(this, f)
    fun <B> map(f: (A) -> B): IO<B> = flatMap { a -> Return(f(a)) }
}

data class Return<A>(val a: A) : IO<A>() // <1>
data class Suspend<A>(val resume: () -> A) : IO<A>() // <2>
data class FlatMap<A, B>(
    val sub: IO<A>,
    val f: (A) -> IO<B>
) : IO<B>() //<3>
//end::init1[]

//tag::init2[]
fun stdout(s: String): IO<Unit> = Suspend { println(s) }

val p = IO.monad()
    .forever<Unit, Unit>(stdout("To infinity and beyond!"))
    .fix()
//end::init2[]

//tag::init3[]
@Suppress("UNCHECKED_CAST")
tailrec fun <A> run(io: IO<A>): A =
    when (io) {
        is Return -> io.a
        is Suspend -> io.resume()
        is FlatMap<*, *> -> { // <1>
            val x = io.sub as IO<A> // <2>
            val f = io.f as (A) -> IO<A> // <2>
            when (x) {
                is Return ->
                    run(f(x.a))
                is Suspend -> // <3>
                    run(f(x.resume()))
                is FlatMap<*, *> -> {
                    val g = x.f as (A) -> IO<A>
                    val y = x.sub as IO<A>
                    run(y.flatMap { a: A -> g(a).flatMap(f) }) // <4>
                }
            }
        }
    }
//end::init3[]

fun main() {
    run(p)
}

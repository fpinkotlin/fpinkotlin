package chapter15.sec2

import chapter10.None
import chapter10.Option
import chapter10.Some
import chapter3.List
import chapter5.Cons
import chapter5.Empty
import chapter5.Stream
import chapter3.Cons as ConsL
import chapter3.Nil as NilL

//tag::init1[]
sealed class Process<I, O> {
    //tag::init2[]
    operator fun invoke(si: Stream<I>): Stream<O> =
        when (this) {
            is Emit -> Cons({ this.head }, { this.tail(si) })
            is Await -> when (si) {
                is Cons -> this.recv(Some(si.head()))(si.tail())
                is Empty -> this.recv(None)(si)
            }
            is Halt -> Stream.empty()
        }

    //end::init2[]
    //tag::init4[]
    fun repeat(): Process<I, O> {
        fun go(p: Process<I, O>): Process<I, O> =
            when (p) {
                is Halt -> go(this) // <1>
                is Await -> Await { i: Option<I> ->
                    when (i) {
                        is None -> p.recv(None) // <2>
                        else -> go(p.recv(i))
                    }
                }
                is Emit -> Emit(p.head, go(p.tail))
            }
        return go(this)
    }
    //end::init4[]
    //driver methods
}

data class Emit<I, O>(
    val head: O,
    val tail: Process<I, O> = Halt() // <1>
) : Process<I, O>()

data class Await<I, O>(
    val recv: (Option<I>) -> Process<I, O>
) : Process<I, O>()

class Halt<I, O> : Process<I, O>()
//end::init1[]

//tag::init3[]
fun <I, O> liftOne(f: (I) -> O): Process<I, O> =
    Await { i: Option<I> ->
        when (i) {
            is Some -> Emit<I, O>(f(i.get))
            is None -> Halt<I, O>()
        }
    }
//end::init3[]

//tag::init5[]
fun <I, O> lift(f: (I) -> O): Process<I, O> = liftOne(f).repeat()
//end::init5[]

//tag::init6[]
fun <I> filter(p: (I) -> Boolean): Process<I, I> =
    Await<I, I> { i: Option<I> ->
        when (i) {
            is Some -> if (p(i.get)) Emit(i.get) else Halt()
            is None -> Halt()
        }
    }.repeat()
//end::init6[]

//tag::init7[]
fun sum(start: Double): Process<Double, Double> {
    fun go(acc: Double): Process<Double, Double> =
        Await { i: Option<Double> ->
            when (i) {
                is Some -> Emit(i.get + acc, go(i.get + acc))
                is None -> Halt<Double, Double>()
            }
        }
    return go(start)
}
//end::init7[]

//tag::init8[]
fun <S, I, O> loop(z: S, f: (I, S) -> Pair<O, S>): Process<I, O> =
    Await { i: Option<I> ->
        when (i) {
            is Some -> {
                val (o, s2) = f(i.get, z)
                Emit(o, loop(s2, f))
            }
            is None -> Halt<I, O>()
        }
    }
//end::init8[]

//boilerplate for main
tailrec fun <A, B> foldLeft(xs: List<A>, z: B, f: (B, A) -> B): B =
    when (xs) {
        is NilL -> z
        is ConsL -> foldLeft(xs.tail, f(z, xs.head), f)
    }

fun <A> reverse(xs: List<A>): List<A> =
    foldLeft(xs, List.empty(), { t: List<A>, h: A -> ConsL(h, t) })

fun <A> Stream<A>.toList(): List<A> {
    tailrec fun go(xs: Stream<A>, acc: List<A>): List<A> = when (xs) {
        is Empty -> acc
        is Cons -> go(xs.tail(), ConsL(xs.head(), acc))
    }
    return reverse(go(this, NilL))
}

fun main() {
    //liftOne
    val p = liftOne<Int, Int> { it * 2 }
    println(p(Stream.of(1, 2, 3, 4, 5)).toList())

    //repeated left
    val units = Stream.continually(Unit)
    println(lift<Unit, Int> { _ -> 1 }(units))

    //filtering
    val even = filter<Int> { it % 2 == 0 }
    println(even(Stream.of(1, 2, 3, 4, 5)).toList())

    //sum
    println(sum(0.0)(Stream.of(1.0, 2.0, 3.0, 4.0)).toList())
}
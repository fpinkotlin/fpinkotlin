package chapter15.sec2

import arrow.core.andThen
import arrow.extension
import chapter10.None
import chapter10.Option
import chapter10.Some
import chapter13.Monad
import chapter13.boilerplate.io.IO
import chapter3.List
import chapter5.Cons
import chapter5.Empty
import chapter5.Stream
import java.io.File
import chapter3.Cons as ConsL
import chapter3.Nil as NilL

class ForProcess private constructor() {
    companion object
}
typealias ProcessOf<I, O> = arrow.Kind2<ForProcess, I, O>
typealias ProcessPartialOf<I> = arrow.Kind<ForProcess, I>

@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
inline fun <I, O> ProcessOf<I, O>.fix(): Process<I, O> =
    this as Process<I, O>

//tag::init1[]
sealed class Process<I, O> : ProcessOf<I, O> {
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
    //tag::init9[]
    fun <O2> map(f: (O) -> O2): Process<I, O2> = this pipe lift(f)

    //end::init9[]
    //tag::init10[]
    infix fun append(p2: Process<I, O>): Process<I, O> =
        when (this) {
            is Halt -> p2
            is Emit -> Emit(this.head, this.tail append p2)
            is Await -> Await { i: Option<I> ->
                (this.recv andThen { p1 -> p1 append p2 })(i)
            }
        }

    //end::init10[]
    //tag::init11[]
    fun <O2> flatMap(f: (O) -> Process<I, O2>): Process<I, O2> =
        when (this) {
            is Halt -> Halt()
            is Emit -> f(this.head) append this.tail.flatMap(f)
            is Await -> Await { i: Option<I> ->
                (this.recv andThen { p -> p.flatMap(f) })(i)
            }
        }

    //end::init11[]
    //tag::ignore[]
    public infix fun <I, O, O2> Process<I, O>.pipe(
        p2: Process<O, O2>
    ): Process<I, O2> =
        when (p2) {
            is Halt -> Halt()
            is Emit -> Emit(p2.head, this pipe p2.tail)
            is Await -> when (this) {
                is Emit -> this.tail pipe p2.recv(Some(this.head))
                is Halt -> Halt<I, O>() pipe p2.recv(None)
                is Await -> Await { i -> this.recv(i) pipe p2 }
            }
        }

    companion object
    //end::ignore[]
    //driver and instance methods
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
fun sum(): Process<Double, Double> {
    fun go(acc: Double): Process<Double, Double> =
        Await { i: Option<Double> ->
            when (i) {
                is Some -> Emit(i.get + acc, go(i.get + acc))
                is None -> Halt<Double, Double>()
            }
        }
    return go(0.0)
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

//tag::init12[]
@extension
interface ProcessMonad<I, O> : Monad<ProcessPartialOf<I>> {

    override fun <A> unit(a: A): ProcessOf<I, A> = Emit(a)

    override fun <A, B> flatMap(
        fa: ProcessOf<I, A>,
        f: (A) -> ProcessOf<I, B>
    ): ProcessOf<I, B> =
        fa.fix().flatMap { a -> f(a).fix() }

    override fun <A, B> map(
        fa: ProcessOf<I, A>,
        f: (A) -> B
    ): ProcessOf<I, B> =
        fa.fix().map(f)
}
//end::init12[]

//tag::init13[]
fun <A, B> processFile(
    file: File,
    proc: Process<String, A>,
    z: B,
    fn: (B, A) -> B
): IO<B> = IO { // <1>

    tailrec fun go(
        ss: Iterator<String>,
        curr: Process<String, A>,
        acc: B
    ): B = // <2>
        when (curr) {
            is Halt -> acc
            is Await -> {
                val next =
                    if (ss.hasNext()) curr.recv(Some(ss.next()))
                    else curr.recv(None)
                go(ss, next, acc)
            }
            is Emit -> go(ss, curr.tail, fn(acc, curr.head))
        }

    file.bufferedReader().use { reader -> // <3>
        go(reader.lines().iterator(), proc, z)
    }
}
//end::init13[]

fun <I> exists(f: (I) -> Boolean): Process<I, Boolean> = TODO()
fun <I> count(): Process<I, Int> = TODO()
infix fun <I, O, O2> Process<I, O>.pipe(
    p2: Process<O, O2>
): Process<I, O2> = TODO()

fun listing() {
    val f = File("/path/to/file")
    //tag::init14[]
    val proc = count<String>() pipe exists { it > 40000 }
    processFile(f, proc, false) { a, b -> a || b }
    //end::init14[]
}

fun main() {
    //liftOne
    val p = liftOne<Int, Int> { it * 2 }
    println(p(Stream.of(1, 2, 3, 4, 5)).toList())

    //repeated left
    val units = Stream.continually(Unit)
    println(units)
    println(lift<Unit, Int> { _ -> 1 }(units))

    //filtering
    val even = filter<Int> { it % 2 == 0 }
    println(even(Stream.of(1, 2, 3, 4, 5)).toList())

    //sum
    println(sum()(Stream.of(1.0, 2.0, 3.0, 4.0)).toList())
}

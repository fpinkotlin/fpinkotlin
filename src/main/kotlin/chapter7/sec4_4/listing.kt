package chapter7.sec4_4

import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right
import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import java.util.concurrent.Callable
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicReference
import kotlin.math.sqrt

//tag::init10[]
abstract class Future<A> {
    internal abstract fun invoke(cb: (A) -> Unit) // <1>
}

typealias Par<A> = (ExecutorService) -> Future<A> // <2>
//end::init10[]

//tag::init11[]
fun <A> run(es: ExecutorService, pa: Par<A>): A {
    val ref = AtomicReference<A>() // <1>
    val latch = CountDownLatch(1) // <2>
    pa(es).invoke { a: A ->
        ref.set(a)
        latch.countDown() // <3>
    }
    latch.await() // <4>
    return ref.get() // <5>
}
//end::init11[]

//tag::init20[]
fun <A> run2(es: ExecutorService, pa: Par<A>): A {
    val ref = CompletableFuture<A>() // <1>
    pa(es).invoke { a: A ->
        ref.complete(a) // <2>
    }
    return ref.get() // <3>
}
//end::init20[]

//tag::init12[]
fun <A> unit(a: A): Par<A> =
    { es: ExecutorService ->
        object : Future<A>() {
            override fun invoke(cb: (A) -> Unit) = cb(a) // <1>
        }
    }
//end::init12[]

//tag::init13[]
fun <A> fork(a: () -> Par<A>): Par<A> =
    { es: ExecutorService ->
        object : Future<A>() {
            override fun invoke(cb: (A) -> Unit) =
                eval(es) { a()(es).invoke(cb) } // <1>
        }
    }

fun eval(es: ExecutorService, r: () -> Unit) {
    es.submit(Callable { r() }) // <2>
}
//end::init13[]

val listing1 = {
    //tag::init14[]
    fun <A, B, C> map2(pa: Par<A>, pb: Par<B>, f: (A, B) -> C): Par<C>
    //end::init14[]
        = TODO()
}

//tag::init15[]
fun <A, B, C> map2(pa: Par<A>, pb: Par<B>, f: (A, B) -> C): Par<C> =
    { es: ExecutorService ->
        object : Future<C>() {
            override fun invoke(cb: (C) -> Unit) {
                val ar = AtomicReference<Option<A>>(None) // <1>
                val br = AtomicReference<Option<B>>(None)
                val combiner =
                    Actor<Either<A, B>>(Strategy.from(es)) { eab -> // <2>
                        when (eab) {
                            is Left<A> -> // <3>
                                br.get().fold(
                                    { ar.set(Some(eab.a)) },
                                    { b -> eval(es) { cb(f(eab.a, b)) } }
                                )
                            is Right<B> -> // <4>
                                ar.get().fold(
                                    { br.set(Some(eab.b)) },
                                    { a -> eval(es) { cb(f(a, eab.b)) } }
                                )
                        }
                    }
                pa(es).invoke { a: A -> combiner.send(Left(a)) } // <5>
                pb(es).invoke { b: B -> combiner.send(Right(b)) }
            }
        }
    }
//end::init15[]

val <A> List<A>.head: A
    get() = first()

fun <A, B> asyncF(f: (A) -> B): (A) -> Par<B> =
    { a: A -> unit(f(a)) }

fun <A, B> map(pa: Par<A>, f: (A) -> B): Par<B> =
    map2(pa, pa) { a: A, _ -> f(a) }

fun <A> List<A>.splitAt(idx: Int): Pair<List<A>, List<A>> = // <1>
    this.subList(0, idx) to
        this.subList(idx, this.size)

fun <A> sequence(ps: List<Par<A>>): Par<List<A>> {
    return when {
        ps.isEmpty() -> unit(emptyList<A>())
        ps.size == 1 -> map(ps.head) { listOf(it) }
        else -> {
            val (l, r) = ps.splitAt(ps.size / 2)
            map2(sequence(l), sequence(r)) { la, lb -> la + lb }
        }
    }
}

fun <A, B> parMap(
    ps: List<A>,
    f: (A) -> B
): Par<List<B>> {
    val fbs: List<Par<B>> = ps.map(asyncF(f))
    return sequence(fbs)
}

fun main() {
    //tag::init16[]
    val p: (ExecutorService) -> Future<List<Double>> =
        parMap((1..10).toList()) { sqrt(it.toDouble()) }

    val x: List<Double> =
        run(Executors.newFixedThreadPool(2), p)

    println(x)
    //end::init16[]

    val p1: Par<List<Double>> =
        parMap((1..10).toList()) { sqrt(it.toDouble()) }
    val x1: List<Double> =
        run2(Executors.newFixedThreadPool(2), p1)
    println(x1)
}

package chapter7.solutions.ex4

import java.util.concurrent.Callable
import java.util.concurrent.CompletableFuture.completedFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

typealias Par<A> = (ExecutorService) -> Future<A>

//tag::init[]
fun <A, B> asyncF(f: (A) -> B): (A) -> Par<B> =
    { a: A -> lazyUnit { f(a) } }
//end::init[]

fun <A> unit(a: A): Par<A> =
    { es: ExecutorService -> completedFuture(a) }

fun <A> fork(
    a: () -> Par<A>
): Par<A> =
    { es: ExecutorService ->
        es.submit(Callable<A> { a()(es).get() })
    }

fun <A> lazyUnit(a: () -> A): Par<A> =
    fork { unit(a()) }

fun <A> run(a: Par<A>): A = TODO()

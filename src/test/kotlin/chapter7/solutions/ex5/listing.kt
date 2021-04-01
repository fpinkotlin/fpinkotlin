package chapter7.solutions.ex5

import chapter7.sec1.splitAt
import chapter7.solutions.ex3.TimedMap2Future
import java.util.concurrent.Callable
import java.util.concurrent.CompletableFuture.completedFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

typealias Par<A> = (ExecutorService) -> Future<A>

object Pars {

    fun <A, B> asyncF(f: (A) -> B): (A) -> Par<B> =
        { a: A ->
            lazyUnit { f(a) }
        }

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

    fun <A, B, C> map2(a: Par<A>, b: Par<B>, f: (A, B) -> C): Par<C> =
        { es: ExecutorService -> TimedMap2Future(a(es), b(es), f) }

    //tag::init1[]
    val <T> List<T>.head: T
        get() = first()

    val <T> List<T>.tail: List<T>
        get() = this.drop(1)

    val Nil = listOf<Nothing>()

    fun <A> sequence1(ps: List<Par<A>>): Par<List<A>> =
        when (ps) {
            Nil -> unit(Nil)
            else -> map2(
                ps.head,
                sequence1(ps.tail)
            ) { a: A, b: List<A> ->
                listOf(a) + b
            }
        }
    //end::init1[]

    fun <A, B> map(pa: Par<A>, f: (A) -> B): Par<B> =
        map2(pa, unit(Unit), { a, _ -> f(a) })

    //tag::init2[]
    fun <A> sequence(ps: List<Par<A>>): Par<List<A>> =
        when {
            ps.isEmpty() -> unit(Nil)
            ps.size == 1 -> map(ps.head) { listOf(it) }
            else -> {
                val (l, r) = ps.splitAt(ps.size / 2)
                map2(sequence(l), sequence(r)) { la, lb ->
                    la + lb
                }
            }
        }
    //end::init2[]

    fun <A> run(a: Par<A>): A = TODO()
}

package chapter7.solutions.ex6

import chapter7.solutions.ex3.TimedMap2Future
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
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

    fun <A> fork(a: () -> Par<A>): Par<A> =
        { es: ExecutorService ->
            es.submit(Callable<A> { a()(es).get() })
        }

    fun <A> lazyUnit(a: () -> A): Par<A> =
        fork { unit(a()) }

    fun <A, B, C> map2(a: Par<A>, b: Par<B>, f: (A, B) -> C): Par<C> =
        { es: ExecutorService ->
            val fa = a(es)
            val fb = b(es)
            TimedMap2Future(fa, fb, f)
        }

    val <T> List<T>.head: T
        get() = first()

    val <T> List<T>.tail: List<T>
        get() = this.drop(1)

    fun <A> List<A>.splitAt(
        idx: Int
    ): Pair<PersistentList<A>, PersistentList<A>> =
        this.subList(0, idx).toPersistentList() to
            this.subList(idx, this.size).toPersistentList()

    fun <A, B> map(pa: Par<A>, f: (A) -> B): Par<B> =
        map2(pa, pa, { a: A, _ -> f(a) })

    fun <A> sequence(ps: List<Par<A>>): Par<List<A>> {
        return when {
            ps.isEmpty() -> unit(emptyList())
            ps.size == 1 -> map(ps.head) { listOf(it) }
            else -> {
                val (l, r) = ps.splitAt(ps.size / 2)
                map2(sequence(l), sequence(r)) { la, lb ->
                    la + lb
                }
            }
        }
    }

    //tag::init[]
    fun <A> parFilter(sa: List<A>, f: (A) -> Boolean): Par<List<A>> {
        val pars: List<Par<A>> = sa.map { lazyUnit { it } }
        return map(sequence(pars)) { la: List<A> ->
            la.flatMap { a ->
                if (f(a)) listOf(a) else emptyList()
            }
        }
    }
    //end::init[]
}

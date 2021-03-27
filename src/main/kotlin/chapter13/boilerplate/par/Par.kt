package chapter13.boilerplate.par

import arrow.Kind
import arrow.extension
import chapter13.Monad
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future
import java.util.function.Supplier

class ForPar private constructor() {
    companion object
}
typealias ParOf<A> = arrow.Kind<ForPar, A>

@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
inline fun <A> ParOf<A>.fix(): Par<A> = this as Par<A>

class Par<A>(val run: (ExecutorService) -> Future<A>) : ParOf<A> {
    companion object {
        fun <A> unit(a: A): Par<A> = Par { _ ->
            CompletableFuture.completedFuture(a)
        }

        fun <A> lazyUnit(a: () -> A): Par<A> = Par { es ->
            val cf = CompletableFuture<A>()
            cf.completeAsync(Supplier(a), es)
            cf
        }
    }

    fun <B> flatMap(f: (A) -> Par<B>): Par<B> = Par { es ->
        f(run(es).get()).run(es)
    }
}

@extension
interface ParMonad : Monad<ForPar> {
    override fun <A> unit(a: A): ParOf<A> = Par.unit(a)

    override fun <A, B> flatMap(
        fa: ParOf<A>,
        f: (A) -> ParOf<B>
    ): ParOf<B> =
        fa.fix().flatMap { a -> f(a).fix() }

    override fun <A, B> map(
        fa: ParOf<A>,
        f: (A) -> B
    ): Kind<ForPar, B> =
        fa.fix().flatMap { a -> Par.unit(f(a)) }
}

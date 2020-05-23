package chapter11

import arrow.Kind
import chapter11.sec1.Functor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

interface Monad<F> : Functor<F> {

    fun <A> unit(a: A): Kind<F, A>
    fun <A, B> flatMap(fa: Kind<F, A>, f: (A) -> Kind<F, B>): Kind<F, B>

    override fun <A, B> map(fa: Kind<F, A>, f: (A) -> B): Kind<F, B> =
        flatMap(fa) { a -> unit(f(a)) }

    fun <A, B, C> map2(fa: Kind<F, A>, fb: Kind<F, B>, f: (A, B) -> C) =
        flatMap(fa) { a -> map(fb) { b -> f(a, b) } }
}

// Par HKT

class Par<A>(run: (ExecutorService) -> Future<A>) : ParOf<A> {
    companion object {
        fun <A> unit(a: A): Par<A> = TODO()
        fun <A, B> flatMap(pa: Par<A>, f: (A) -> Par<B>): Par<B> = TODO()
    }
}

class ForPar private constructor() {
    companion object
}

typealias ParOf<A> = Kind<ForPar, A>

fun <A> ParOf<A>.fix(): Par<A> = this as Par<A>


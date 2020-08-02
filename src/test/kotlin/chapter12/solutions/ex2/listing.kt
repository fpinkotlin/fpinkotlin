package chapter12.solutions.ex2

import arrow.Kind
import arrow.syntax.function.curried
import chapter12.Functor

//tag::init1[]
interface Applicative<F> : Functor<F> {

    fun <A, B> apply(
        fab: Kind<F, (A) -> B>,
        fa: Kind<F, A>
    ): Kind<F, B> = // <1>
        map2(fa, fab) { a, f -> f(a) }

    fun <A> unit(a: A): Kind<F, A>

    override fun <A, B> map(
        fa: Kind<F, A>,
        f: (A) -> B
    ): Kind<F, B> = // <2>
        apply(unit(f), fa)

    fun <A, B, C> map2(
        fa: Kind<F, A>,
        fb: Kind<F, B>,
        f: (A, B) -> C
    ): Kind<F, C> = // <3>
        apply(apply(unit(f.curried()), fa), fb)
}
//end::init1[]

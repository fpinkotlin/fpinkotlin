package chapter12.exercises.ex11

import arrow.Kind
import arrow.syntax.function.curried
import chapter12.Functor
import utils.SOLUTION_HERE

interface Applicative<F> : Functor<F> {

    override fun <A, B> map(
        fa: Kind<F, A>,
        f: (A) -> B
    ): Kind<F, B> =
        map2(fa, unit(Unit)) { a, _ -> f(a) }

    fun <A, B> apply(
        fab: Kind<F, (A) -> B>,
        fa: Kind<F, A>
    ): Kind<F, B>

    fun <A> unit(a: A): Kind<F, A>

    fun <A, B, C> map2(
        fa: Kind<F, A>,
        fb: Kind<F, B>,
        f: (A, B) -> C
    ): Kind<F, C> =
        apply(apply(unit(f.curried()), fa), fb)

    //tag::init1[]
    fun <K, V> sequence(
        mkv: Map<K, Kind<F, V>>
    ): Kind<F, Map<K, V>> =

        SOLUTION_HERE()
    //end::init1[]
}

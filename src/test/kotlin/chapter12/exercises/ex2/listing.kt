package chapter12.exercises.ex2

import arrow.Kind
import chapter12.Functor
import utils.SOLUTION_HERE

//tag::init1[]
interface Applicative<F> : Functor<F> {

    fun <A, B> apply(
        fab: Kind<F, (A) -> B>,
        fa: Kind<F, A>
    ): Kind<F, B> =

        SOLUTION_HERE("Define in terms of map2 and unit")

    fun <A> unit(a: A): Kind<F, A>

    override fun <A, B> map(
        fa: Kind<F, A>,
        f: (A) -> B
    ): Kind<F, B> =

        SOLUTION_HERE("Define in terms of apply and unit")

    fun <A, B, C> map2(
        fa: Kind<F, A>,
        fb: Kind<F, B>,
        f: (A, B) -> C
    ): Kind<F, C> =

        SOLUTION_HERE("Define in terms of apply and unit")
}
//end::init1[]

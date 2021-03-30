package chapter12.exercises.ex3

import arrow.Kind
import chapter12.Functor
import utils.SOLUTION_HERE

interface Applicative<F> : Functor<F> {

    fun <A, B> apply(
        fab: Kind<F, (A) -> B>,
        fa: Kind<F, A>
    ): Kind<F, B>

    fun <A> unit(a: A): Kind<F, A>

    //tag::init1[]
    fun <A, B, C, D> map3(
        fa: Kind<F, A>,
        fb: Kind<F, B>,
        fc: Kind<F, C>,
        f: (A, B, C) -> D
    ): Kind<F, D> =

        SOLUTION_HERE()

    fun <A, B, C, D, E> map4(
        fa: Kind<F, A>,
        fb: Kind<F, B>,
        fc: Kind<F, C>,
        fd: Kind<F, D>,
        f: (A, B, C, D) -> E
    ): Kind<F, E> =

        SOLUTION_HERE()
    //end::init1[]
}

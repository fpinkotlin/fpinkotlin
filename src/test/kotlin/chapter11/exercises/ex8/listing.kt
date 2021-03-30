package chapter11.exercises.ex8

import arrow.Kind
import chapter11.Functor
import utils.SOLUTION_HERE

interface Monad<F> : Functor<F> {

    fun <A> unit(a: A): Kind<F, A>

    fun <A, B, C> compose(
        f: (A) -> Kind<F, B>,
        g: (B) -> Kind<F, C>
    ): (A) -> Kind<F, C>

    //tag::init[]
    fun <A, B> flatMap(
        fa: Kind<F, A>,
        f: (A) -> Kind<F, B>
    ): Kind<F, B> =

        SOLUTION_HERE()
    //end::init[]
}

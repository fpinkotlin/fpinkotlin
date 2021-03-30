package chapter11.exercises.ex12

import arrow.Kind
import chapter11.Functor
import utils.SOLUTION_HERE

interface Monad<F> : Functor<F> {

    fun <A> unit(a: A): Kind<F, A>

    override fun <A, B> map(fa: Kind<F, A>, f: (A) -> B): Kind<F, B> =
        flatMap(fa) { a -> unit(f(a)) }

    fun <A> join(mma: Kind<F, Kind<F, A>>): Kind<F, A> =
        flatMap(mma) { ma -> ma }

    //tag::init1[]
    fun <A, B> flatMap(fa: Kind<F, A>, f: (A) -> Kind<F, B>): Kind<F, B> =

        SOLUTION_HERE()
    //end::init1[]

    //tag::init2[]
    fun <A, B, C> compose(
        f: (A) -> Kind<F, B>,
        g: (B) -> Kind<F, C>
    ): (A) -> Kind<F, C> =

        SOLUTION_HERE()
    //end::init2[]
}

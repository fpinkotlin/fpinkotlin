package chapter12.sec6

import arrow.Kind
import arrow.typeclasses.Applicative
import chapter12.Functor
import chapter12.List

interface Applicative<F> : Functor<F> {

    //tag::init1[]
    fun <A, B> traverse(l: List<A>, f: (A) -> Kind<F, B>): Kind<F, List<B>>
    //end::init1[]
        = TODO()

    //tag::init2[]
    fun <A> sequence(lfa: List<Kind<F, A>>): Kind<F, List<A>>
    //end::init2[]
        = TODO()
}

//tag::init3[]
interface Traversable<F> : Functor<F> {

    fun <G, A, B> traverse(
        fa: Kind<F, A>,
        AG: Applicative<G>, // <1>
        f: (A) -> Kind<G, B>
    ): Kind<G, Kind<F, B>> =
        sequence(map(fa, f), AG)

    fun <G, A> sequence(
        fga: Kind<F, Kind<G, A>>,
        AG: Applicative<G> // <1>
    ): Kind<G, Kind<F, A>> = // <2>
        traverse(fga, AG) { it }
}
//end::init3[]
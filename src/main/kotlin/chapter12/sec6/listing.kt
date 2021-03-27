package chapter12.sec6

import arrow.Kind
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
interface Traversable<F> : Functor<F> { // <1>

    fun <G, A, B> traverse(
        fa: Kind<F, A>,
        AG: Applicative<G>, // <2>
        f: (A) -> Kind<G, B>
    ): Kind<G, Kind<F, B>> =
        sequence(map(fa, f), AG)

    fun <G, A> sequence(
        fga: Kind<F, Kind<G, A>>,
        AG: Applicative<G> // <2>
    ): Kind<G, Kind<F, A>> = // <3>
        traverse(fga, AG) { it }
}
//end::init3[]

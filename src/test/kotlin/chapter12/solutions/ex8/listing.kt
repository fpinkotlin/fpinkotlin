package chapter12.solutions.ex8

import arrow.Kind
import chapter12.Applicative

//tag::init1[]

fun <F, G> product(
    af: Applicative<F>,
    ag: Applicative<G>
): Applicative<Pair<F, G>> = object : Applicative<Pair<F, G>> {
    override fun <A, B> apply(
        fab: Kind<Pair<F, G>, (A) -> B>,
        fa: Kind<Pair<F, G>, A>
    ): Kind<Pair<F, G>, B> {
        TODO()
    }

    override fun <A> unit(a: A): Kind<Pair<F, G>, A> = TODO()
}

//end::init1[]

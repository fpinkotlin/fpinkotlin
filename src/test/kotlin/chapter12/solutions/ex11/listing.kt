package chapter12.solutions.ex11

import arrow.Kind
import chapter11.Monad

//tag::init1[]
fun <F, G> compose(mf: Monad<F>, mg: Monad<G>): Monad<Kind<F, G>> = TODO()
//end::init1[]

interface Listing<F, G> {

    val F: Monad<F>
    val G: Monad<G>

/*
    //tag::init2[]
    fun <A, B> flatMap(
        mna: Kind<F, Kind<G, A>>,
        f: (A) -> Kind<F, Kind<G, B>>
    ): Kind<F, Kind<G, B>> =
        F.flatMap(mna) { na: Kind<G, A> ->
            G.flatMap(na) { a: A ->
                f(a)
            }
        }
    //end::init2[]
*/
}

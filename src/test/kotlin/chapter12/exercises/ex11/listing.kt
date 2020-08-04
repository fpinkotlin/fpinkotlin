package chapter12.exercises.ex11

import arrow.Kind
import chapter11.Monad
import chapter12.CompositeOf
import chapter12.CompositePartialOf
import chapter12.fix

interface Listing<F, G> {

    //tag::init1[]
    fun <F, G> compose(
        mf: Monad<F>,
        mg: Monad<G>
    ): Monad<CompositePartialOf<F, G>> = TODO()
    //end::init1[]
}

/*
//tag::init2[]
fun <A, B> flatMap(
    mna: CompositeOf<F, G, A>,
    f: (A) -> CompositeOf<F, G, B>
): CompositeOf<F, G, B> =
    mf.flatMap(mna.fix().value) { na: Kind<G, A> ->
        mg.flatMap(na) { a: A ->
            f(a)
        }
    }
//end::init2[]
*/

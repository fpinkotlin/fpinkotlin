package chapter12.exercises.ex10

import chapter11.Monad
import chapter12.CompositePartialOf

interface Listing<F, G> {

    //tag::init1[]
    fun <F, G> compose(
        mf: Monad<F>,
        mg: Monad<G>
    ): Monad<CompositePartialOf<F, G>> = TODO()
    //end::init1[]
}

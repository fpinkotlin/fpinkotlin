package chapter12.exercises.ex19

import chapter11.Monad
import chapter12.Applicative
import chapter12.CompositePartialOf
import chapter12.Traversable

//tag::init[]
fun <G, H, A> composeM(
    MG: Monad<G>,
    MH: Monad<H>,
    AH: Applicative<H>,
    TH: Traversable<H>
): Monad<CompositePartialOf<G, H>> = TODO()
//end::init[]

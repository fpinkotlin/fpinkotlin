package chapter12.exercises.ex19

import arrow.Kind
import chapter11.Monad
import chapter12.Applicative
import chapter12.CompositePartialOf
import chapter12.Traversable
import utils.SOLUTION_HERE

fun <F> applicative() = object : Applicative<F> {
    override fun <A> unit(a: A): Kind<F, A> =
        SOLUTION_HERE()
}

//tag::init[]
fun <G, H, A> composeM(
    MG: Monad<G>,
    MH: Monad<H>,
    AH: Applicative<H>,
    TH: Traversable<H>
): Monad<CompositePartialOf<G, H>> =

    SOLUTION_HERE()
//end::init[]

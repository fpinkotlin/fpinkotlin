package chapter12.solutions.ex11

import arrow.Kind
import chapter11.Monad

//tag::init1[]
fun <F, G> compose(mf: Monad<F>, mg: Monad<G>): Monad<Kind<F, G>> = TODO()
//end::init1[]
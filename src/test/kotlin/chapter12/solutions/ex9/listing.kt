package chapter12.solutions.ex9

import arrow.Kind
import chapter11.sec5_2.Kind2
import chapter12.Applicative

//tag::init1[]
fun <F, G, A> compose(
    af: Applicative<Kind<F, A>>,
    ag: Applicative<Kind<G, A>>
): Applicative<Kind2<F, G, A>> = TODO()
//end::init1[]

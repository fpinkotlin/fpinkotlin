package chapter12.exercises.ex8

import chapter12.Applicative
import chapter12.ProductPartialOf

//tag::init1[]
fun <F, G> product(
    AF: Applicative<F>,
    AG: Applicative<G>
): Applicative<ProductPartialOf<F, G>> = TODO()
//end::init1[]

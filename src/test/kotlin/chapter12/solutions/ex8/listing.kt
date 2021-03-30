package chapter12.solutions.ex8

import chapter12.Applicative
import chapter12.Product
import chapter12.ProductOf
import chapter12.ProductPartialOf
import chapter12.fix

//tag::init1[]
fun <F, G> product(
    AF: Applicative<F>,
    AG: Applicative<G>
): Applicative<ProductPartialOf<F, G>> =
    object : Applicative<ProductPartialOf<F, G>> {

        override fun <A, B> apply(
            fgab: ProductOf<F, G, (A) -> B>,
            fga: ProductOf<F, G, A>
        ): ProductOf<F, G, B> {
            val (fab, gab) = fgab.fix().value
            val (fa, ga) = fga.fix().value
            return Product(AF.apply(fab, fa) to AG.apply(gab, ga))
        }

        override fun <A> unit(a: A): ProductOf<F, G, A> =
            Product(AF.unit(a) to AG.unit(a))
    }

//end::init1[]

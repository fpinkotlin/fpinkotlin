package chapter12.solutions.ex18

import arrow.Kind
import chapter12.Applicative
import chapter12.Composite
import chapter12.CompositeOf
import chapter12.CompositePartialOf
import chapter12.Traversable
import chapter12.fix

//tag::init[]
fun <F, G> compose(
    TF: Traversable<F>,
    TG: Traversable<G>
): Traversable<CompositePartialOf<F, G>> =
    object : Traversable<CompositePartialOf<F, G>> {
        override fun <H, A, B> traverse(
            fa: CompositeOf<F, G, A>,
            AH: Applicative<H>,
            f: (A) -> Kind<H, B>
        ): Kind<H, CompositeOf<F, G, B>> =
            AH.map(
                TF.traverse(fa.fix().value, AH) { ga: Kind<G, A> ->
                    TG.traverse(ga, AH) { a: A -> f(a) }
                }
            ) { Composite(it) }
    }
//end::init[]

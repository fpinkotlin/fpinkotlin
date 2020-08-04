package chapter12.exercises.ex18

import arrow.Kind
import chapter12.Applicative
import chapter12.CompositeOf
import chapter12.CompositePartialOf
import chapter12.Traversable

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
        ): Kind<H, CompositeOf<F, G, B>> = TODO()
    }
//end::init[]

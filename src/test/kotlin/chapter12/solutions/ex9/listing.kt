package chapter12.solutions.ex9

import arrow.Kind
import chapter12.Applicative
import chapter12.Composite
import chapter12.CompositeOf
import chapter12.CompositePartialOf
import chapter12.fix

//tag::init1[]
fun <F, G> compose(
    AF: Applicative<F>,
    AG: Applicative<G>
): Applicative<CompositePartialOf<F, G>> =
    object : Applicative<CompositePartialOf<F, G>> {

        override fun <A> unit(a: A): CompositeOf<F, G, A> =
            Composite(AF.unit(AG.unit(a)))

        override fun <A, B, C> map2(
            fa: CompositeOf<F, G, A>,
            fb: CompositeOf<F, G, B>,
            f: (A, B) -> C
        ): CompositeOf<F, G, C> {
            val value = AF.map2(
                fa.fix().value,
                fb.fix().value
            ) { ga: Kind<G, A>, gb: Kind<G, B> ->
                AG.map2(ga, gb) { a: A, b: B ->
                    f(a, b)
                }
            }
            return Composite(value)
        }
    }
//end::init1[]

package chapter12.solutions.ex19

import arrow.Kind
import chapter11.Monad
import chapter12.Applicative
import chapter12.Composite
import chapter12.CompositeOf
import chapter12.CompositePartialOf
import chapter12.Traversable
import chapter12.fix

//tag::init[]
fun <G, H, A> composeM(
    MG: Monad<G>,
    MH: Monad<H>,
    AH: Applicative<H>,
    TH: Traversable<H>
): Monad<CompositePartialOf<G, H>> =
    object : Monad<CompositePartialOf<G, H>> {

        override fun <A> unit(a: A): CompositeOf<G, H, A> =
            Composite(MG.unit(MH.unit(a)))

        override fun <A, B> flatMap(
            mna: CompositeOf<G, H, A>,
            f: (A) -> CompositeOf<G, H, B>
        ): CompositeOf<G, H, B> {
            return Composite(
                MG.map(mna.fix().value) { na: Kind<H, A> ->
                    val traverse: Kind<H, Kind<H, B>> =
                        TH.traverse<H, A, B>(na, AH) { a ->
                            f(a).fix().value
                            TODO("Deal with this incompatibility!")
                        }
                    MH.join(traverse)
                }
            )
        }

        override fun <A, B, C> compose(
            f: (A) -> Kind<CompositePartialOf<G, H>, B>,
            g: (B) -> Kind<CompositePartialOf<G, H>, C>
        ): (A) -> Kind<CompositePartialOf<G, H>, C> {
            TODO()
        }
    }
//end::init[]

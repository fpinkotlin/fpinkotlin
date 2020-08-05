package chapter12.solutions.ex19

import arrow.Kind
import chapter11.Monad
import chapter12.Applicative
import chapter12.Composite
import chapter12.CompositeOf
import chapter12.CompositePartialOf
import chapter12.Traversable
import chapter12.fix

fun <F> applicative() = object : Applicative<F> {
    override fun <A> unit(a: A): Kind<F, A> = TODO()
}

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
            cgha: CompositeOf<G, H, A>,
            f: (A) -> CompositeOf<G, H, B>
        ): CompositeOf<G, H, B> =
            Composite( // <8>
                MG.join( // <7>
                    MG.map(cgha.fix().value) { ha -> // <6>
                        MG.map( // <5>
                            TH.sequence( // <4>
                                MH.map( // <3>
                                    AH.apply( // <2>
                                        AH.unit(f), // <1>
                                        ha
                                    )
                                ) { cghbc ->
                                    cghbc.fix().value // <3>
                                }, applicative() // <4>
                            )
                        ) { MH.join(it) } // <5>
                    }
                )
            )
    }
//end::init[]

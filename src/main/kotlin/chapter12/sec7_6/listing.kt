package chapter12.sec7_6

import arrow.Kind
import chapter10.ForOption
import chapter10.None
import chapter10.OptionOf
import chapter10.Some
import chapter10.fix
import chapter11.Monad

//tag::init[]
data class OptionT<M, A>(
    val value: Kind<M, Kind<ForOption, A>>, // <1>
    val MM: Monad<M> // <2>
) {
    fun <B> flatMap(f: (A) -> OptionT<M, B>): OptionT<M, B> =
        OptionT(MM.flatMap(value) { ooa: OptionOf<A> ->
            when (val o = ooa.fix()) {
                is None -> MM.unit(None)
                is Some -> f(o.get).value
            }
        }, MM)
}
//end::init[]

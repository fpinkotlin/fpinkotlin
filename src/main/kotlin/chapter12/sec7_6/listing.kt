package chapter12.sec7_6

import arrow.Kind
import chapter10.List
import chapter10.None
import chapter10.Option
import chapter10.Some
import chapter10.fix
import chapter11.Monad
import chapter11.listMonad

//tag::init1[]
data class OptionT<M, A>(
    val value: Kind<M, Option<A>>, // <1>
    val MM: Monad<M> // <2>
) {
    fun <B> flatMap(f: (A) -> OptionT<M, B>): OptionT<M, B> =
        OptionT(MM.flatMap(value) { oa: Option<A> ->
            when (oa) {
                is None -> MM.unit(None)
                is Some -> f(oa.get).value
            }
        }, MM)
}
//end::init1[]

val listing = {
    //tag::init2[]
    val F = listMonad
    val loi = List.of(Some(1), None, Some(2))
    val los: List<Option<String>> =
        OptionT(loi, F).flatMap { i: Int ->
            OptionT(F.unit(Some("$i")), F)
        }.value.fix()
    assert(los == List.of(Some("1"), Some("2")))
    //end::init2[]
}
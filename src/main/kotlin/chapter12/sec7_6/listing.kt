package chapter12.sec7_6

import arrow.Kind
import chapter10.List
import chapter10.None
import chapter10.Option
import chapter10.Some
import chapter10.fix
import chapter11.Monad
import chapter11.listMonad
import chapter12.assertEqual

//tag::init1[]
data class OptionT<M, A>(
    val value: Kind<M, Option<A>>, // <1>
    val MM: Monad<M> // <2>
) {
    fun <B> flatMap(f: (A) -> OptionT<M, B>): OptionT<M, B> = // <3>
        OptionT(MM.flatMap(value) { oa: Option<A> ->
            when (oa) {
                is None -> MM.unit(None)
                is Some -> f(oa.get).value
            }
        }, MM)
}
//end::init1[]

fun main() {
    //tag::init2[]
    val F = listMonad
    val ls = List.of(Some(1), None, Some(2)) // <1>
    val xs: List<Option<String>> =
        OptionT(ls, F).flatMap { i: Int -> // <2>
            OptionT(F.unit(Some("${i * 2}")), F) // <3>
        }.value.fix()

    assertEqual(xs, List.of(Some("2"), None, Some("4")))
    //end::init2[]
}

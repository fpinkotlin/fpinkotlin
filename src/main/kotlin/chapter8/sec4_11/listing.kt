package chapter8.sec4_11

import chapter7.sec4.Par
import chapter8.Gen
import chapter8.Prop
import chapter8.Prop.Companion.forAll
import chapter8.sec4_10.ges
import chapter8.sec4_10.map2

//tag::init1[]
fun <A, B> combine(ga: Gen<A>, gb: Gen<B>): Gen<Pair<A, B>> =
    map2(ga, gb) { a, b -> a to b }
//end::init1[]

//tag::init2[]
fun <A> forAllPar(ga: Gen<A>, f: (A) -> Par<Boolean>): Prop =
    forAll(
        combine(ges, ga)
    ) { esa ->
        val (es, a) = esa
        f(a)(es).get()
    }
//end::init2[]

package chapter8.sec4.listing11

import chapter7.sec4.Par
import chapter8.sec3.listing3.Gen
import chapter8.sec3.listing3.Prop
import chapter8.sec3.listing3.Prop.Companion.forAll
import chapter8.sec4.listing10.ges
import chapter8.sec4.listing10.map2

//tag::init1[]
fun <A, B> combine(ga: Gen<A>, gb: Gen<B>): Gen<Pair<A, B>> =
    map2(ga, gb) { es, a -> es to a }
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

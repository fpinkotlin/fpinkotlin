package chapter8.sec4_13

import chapter7.sec4.Par
import chapter8.sec3_3.Gen
import chapter8.sec3_3.Prop
import chapter8.sec3_3.Prop.Companion.forAll
import chapter8.sec4_12.combine
import chapter8.sec4_12.ges

//tag::init[]
fun <A> forAllPar(ga: Gen<A>, f: (A) -> Par<Boolean>): Prop =
    forAll(ges combine ga) { (es, a) ->
        f(a)(es).get()
    }
//end::init[]

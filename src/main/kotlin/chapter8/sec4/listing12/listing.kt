package chapter8.sec4.listing12

import chapter7.sec4.Par
import chapter8.sec3.listing3.Gen
import chapter8.sec3.listing3.Prop
import chapter8.sec3.listing3.Prop.Companion.forAll
import chapter8.sec4.listing10.map2
import java.util.concurrent.ExecutorService

//tag::init1[]
infix fun <A, B> Gen<A>.combine(gb: Gen<B>): Gen<Pair<A, B>> =
    map2(this, gb) { s, a -> s to a }
//end::init1[]

val ges: Gen<ExecutorService> = TODO()

//tag::init2[]
fun <A> forAllPar(ga: Gen<A>, f: (A) -> Par<Boolean>): Prop =
    forAll(ges combine ga) { esa ->
        val (es, a) = esa
        f(a)(es).get()
    }
//end::init2[]

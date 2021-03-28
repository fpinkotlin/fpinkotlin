package chapter8.sec4_3

import chapter7.sec3.Pars
import chapter7.sec4.map
import chapter8.Gen
import chapter8.Prop
import java.util.concurrent.Executors

fun <A> forAll(ga: Gen<A>, f: (A) -> Boolean): Prop = TODO()
val listing2 = {
    //tag::init[]
    val es = Executors.newCachedThreadPool()
    val p1 = forAll(Gen.unit(Pars.unit(1))) { pi ->
        map(pi, { it + 1 })(es).get() == Pars.unit(2)(es).get()
    }
    //end::init[]
}

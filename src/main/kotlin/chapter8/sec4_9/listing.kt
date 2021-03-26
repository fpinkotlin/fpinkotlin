package chapter8.sec4_9

import chapter7.sec4.Par
import chapter7.sec4.map
import chapter7.sec4.unit
import chapter8.Falsified
import chapter8.Passed
import chapter8.Prop
import java.util.concurrent.Executors

fun check(p: () -> Boolean): Prop = Prop { _, _, _ ->
    if (p()) Passed else Falsified("()", 0)
}

val es = Executors.newCachedThreadPool()

fun <A, B, C> map2(pa: Par<A>, pb: Par<B>, f: (A, B) -> C): Par<C> =
    TODO()

//tag::init[]
fun <A> equal(p1: Par<A>, p2: Par<A>): Par<Boolean> =
    map2(p1, p2, { a, b -> a == b })

val p = check {
    val p1 = map(unit(1)) { it + 1 }
    val p2 = unit(2)
    equal(p1, p2)(es).get()
}
//end::init[]

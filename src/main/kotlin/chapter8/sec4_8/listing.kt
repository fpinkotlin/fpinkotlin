package chapter8.sec4_8

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

val listing = {
    //tag::init[]
    val p = check {
        val p1 = map(unit(1)) { it + 1 }
        val p2 = unit(2)
        p1(es).get() == p2(es).get()
    }
    //end::init[]
}

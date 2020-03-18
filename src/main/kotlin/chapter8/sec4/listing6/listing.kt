package chapter8.sec4.listing6

import chapter8.Falsified
import chapter8.Passed
import chapter8.sec3.listing3.Prop

val listing5 = {
    //tag::init[]
    fun check(p: () -> Boolean): Prop =
        Prop { _, _, _ ->
            if (p()) Passed
            else Falsified("()", 0)
        }
    //end::init[]
}

package chapter8.sec2_4

import io.kotlintest.properties.Gen

class Prop

val listing4 = {
    //tag::init[]
    fun <A> forAll(a: Gen<A>, f: (A) -> Boolean): Prop = TODO()
    //end::init[]
}

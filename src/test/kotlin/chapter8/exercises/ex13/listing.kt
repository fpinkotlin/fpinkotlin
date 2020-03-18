package chapter8.exercises.ex13

import chapter8.sec3.listing3.Gen
import chapter8.sec3.listing3.SGen
import chapter8.sec4.listing1.run

fun main() {
    //tag::init1[]
    fun <A> nonEmptyListOf(ga: Gen<A>): SGen<List<A>> = TODO()
    //end::init1[]

    val smallInt = Gen.choose(-10, 10)

    //tag::init2[]
    val maxProp = TODO()
    //end::init2[]
    run(maxProp)
}

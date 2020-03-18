package chapter8.solutions.ex13

import arrow.core.extensions.list.foldable.exists
import chapter8.sec3.listing3.Gen
import chapter8.sec3.listing3.Prop
import chapter8.sec3.listing3.SGen
import chapter8.sec4.listing1.run
import kotlin.math.max

fun main() {
    //tag::init1[]
    fun <A> nonEmptyListOf(ga: Gen<A>): SGen<List<A>> =
        SGen { i -> Gen.listOfN(max(1, i), ga) }
    //end::init1[]

    val smallInt = Gen.choose(-10, 10)

    //tag::init2[]
    val maxProp =
        Prop.forAll(nonEmptyListOf(smallInt)) { ns: List<Int> ->
            val mx = ns.max()
                ?: throw IllegalStateException("max on empty list")
            !ns.exists { it > mx }
        }
    //end::init2[]
    run(maxProp)
}

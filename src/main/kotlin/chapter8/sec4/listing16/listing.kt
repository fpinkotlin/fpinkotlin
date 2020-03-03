package chapter8.sec4.listing16

import arrow.core.extensions.list.foldable.forAll
import chapter8.sec3.listing3.Gen
import chapter8.sec3.listing3.Prop.Companion.forAll

val listing = {

    val n = 10
    val ga = Gen.choose(1, 10)
    //tag::init1[]
    val isEven = { i: Int -> i % 2 == 0 }

    val takeWhileProp =
        forAll(Gen.listOfN(n, ga)) { ns ->
            ns.takeWhile(isEven).forAll(isEven)
        }
    //end::init1[]

    //tag::init2[]
    fun genStringIntFn(g: Gen<Int>): Gen<(String) -> Int> =
        g.map { i -> { s: String -> i } }
    //end::init2[]
}

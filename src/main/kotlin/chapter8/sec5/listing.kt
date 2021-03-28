package chapter8.sec5

import arrow.core.extensions.list.foldable.forAll
import chapter8.Gen
import chapter8.Prop
import chapter8.sec4_1.run

val listing = {

    val n = 10
    val ga = Gen.choose(1, 10)
    //tag::init1[]
    val isEven = { i: Int -> i % 2 == 0 }

    val takeWhileProp =
        Prop.forAll(Gen.listOfN(n, ga)) { ns ->
            ns.takeWhile(isEven).forAll(isEven)
        }
    //end::init1[]

    //tag::init2[]
    fun genStringIntFn(g: Gen<Int>): Gen<(String) -> Int> =
        g.map { i -> { _: String -> i } }
    //end::init2[]

    //tag::init3[]
    fun genIntBooleanFn(g: Gen<Boolean>): Gen<(Int) -> Boolean> =
        g.map { b: Boolean -> { _: Int -> b } }
    //end::init3[]
}

fun main() {
    //tag::init4[]
    fun genIntBooleanFn(t: Int): Gen<(Int) -> Boolean> =
        Gen.unit { i: Int -> i > t }
    //end::init4[]

    //tag::init5[]
    val gen: Gen<Boolean> =
        Gen.listOfN(100, Gen.choose(1, 100)).flatMap { ls: List<Int> ->
            Gen.choose(1, ls.size / 2).flatMap { threshold: Int ->
                genIntBooleanFn(threshold).map { fn: (Int) -> Boolean ->
                    ls.takeWhile(fn).forAll(fn)
                }
            }
        }
    //end::init5[]

    //tag::init6[]
    run(Prop.forAll(gen) { success -> success })
    //end::init6[]
}

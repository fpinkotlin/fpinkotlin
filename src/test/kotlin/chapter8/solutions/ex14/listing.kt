package chapter8.solutions.ex14

import arrow.core.extensions.list.foldable.exists
import chapter8.Gen
import chapter8.Prop.Companion.forAll
import chapter8.SGen
import chapter8.sec4_1.run

val smallInt = Gen.choose(-10, 10)

fun List<Int>.prepend(i: Int) = listOf(i) + this

//tag::init[]
val maxProp = forAll(SGen.listOf(smallInt)) { ns ->
    val nss = ns.sorted()
    nss.isEmpty() or // <1>
            (nss.size == 1) or // <2>
            nss.zip(nss.prepend(Int.MIN_VALUE))
                .foldRight(true) { p, b ->
                    val (pa, pb) = p
                    b && (pa >= pb)
                } and // <3>
            nss.containsAll(ns) and // <4>
            !nss.exists { !ns.contains(it) } // <5>
}
//end::init[]

fun main() {
    run(maxProp)
}

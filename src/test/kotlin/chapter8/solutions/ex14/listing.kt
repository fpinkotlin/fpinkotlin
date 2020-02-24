package chapter8.solutions.ex14

import arrow.core.extensions.list.foldable.exists
import chapter8.sec3.listing3.Gen
import chapter8.sec3.listing3.SGen
import chapter8.sec3.listing3.forAll
import chapter8.sec4.listing1.run

val smallInt = Gen.choose(-10, 10)

fun List<Int>.prepend(i: Int) = listOf(i) + this

//tag::init[]
val maxProp = forAll(SGen.listOf(smallInt)) { ns ->
    val nss = ns.sorted()
    //list may be empty
    nss.isEmpty() ||
            //list may have single element
            nss.size == 1 ||
            //list must be sorted
            nss.zip(nss.prepend(Int.MIN_VALUE))
                .foldRight(true, { p, b ->
                    val (pa, pb) = p
                    b && (pa >= pb)
                }) &&
            //list must contain all elements as unsorted list
            nss.containsAll(ns) &&
            //list must not contain elements not in unsorted list
            !nss.exists { !ns.contains(it) }
}
//end::init[]

fun main() {
    run(maxProp)
}


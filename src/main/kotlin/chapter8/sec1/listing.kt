package chapter8.sec1

import arrow.core.extensions.list.foldable.firstOption
import arrow.core.lastOrNone
import io.kotlintest.properties.Gen
import io.kotlintest.properties.forAll

fun main() {
    //tag::init1[]
    val intList = Gen.list(Gen.choose(0, 100)) // <1>

    forAll(intList) { // <2>
        (it.reversed().reversed() == it) and // <3>
                (it.firstOption() == it.reversed().lastOrNone()) // <4>
    }

    forAll(intList) { // <5>
        it.reversed() == it
    }
    //end::init1[]
}

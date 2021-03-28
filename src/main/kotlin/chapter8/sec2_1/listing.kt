package chapter8.sec2_1

import arrow.core.extensions.list.foldable.firstOption
import arrow.core.lastOrNone
import io.kotlintest.properties.Gen
import io.kotlintest.properties.forAll

val listing1 = {
    //tag::init[]
    val intList = Gen.list(Gen.choose(0, 100))

    forAll(intList) {
        (it.reversed().reversed() == it) and
                (it.firstOption() == it.reversed().lastOrNone())
    }
    //end::init[]
}

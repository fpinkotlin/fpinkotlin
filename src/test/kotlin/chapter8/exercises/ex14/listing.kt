package chapter8.exercises.ex14

import arrow.core.extensions.list.foldable.exists
import chapter8.sec3.listing3.Gen
import chapter8.sec3.listing3.Prop
import chapter8.sec3.listing3.Prop.Companion.forAll
import chapter8.sec3.listing3.SGen
import chapter8.sec4.listing1.run

val smallInt = Gen.choose(-10, 10)

fun List<Int>.prepend(i: Int) = listOf(i) + this

val maxProp: Prop = TODO()

fun main() {
    run(maxProp)
}


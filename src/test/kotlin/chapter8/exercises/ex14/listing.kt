package chapter8.exercises.ex14

import chapter8.Gen
import chapter8.Prop
import chapter8.sec4_1.run

val smallInt = Gen.choose(-10, 10)

fun List<Int>.prepend(i: Int) = listOf(i) + this

val maxProp: Prop = TODO()

fun main() {
    run(maxProp)
}

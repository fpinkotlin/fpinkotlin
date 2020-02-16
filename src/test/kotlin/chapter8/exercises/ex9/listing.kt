package chapter8.exercises.ex9

import chapter8.RNG

typealias TestCases = Int

sealed class Result

data class Prop(val run: (TestCases, RNG) -> Result)

//tag::init[]
fun and(p: Prop): Prop = TODO()

fun or(p: Prop): Prop = TODO()
//end::init[]

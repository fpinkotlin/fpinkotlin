package chapter10.exercises.ex10

import chapter10.Monoid
import utils.SOLUTION_HERE

sealed class WC
data class Stub(val chars: String) : WC()
data class Part(val ls: String, val words: Int, val rs: String) : WC()

//tag::init1[]
fun wcMonoid(): Monoid<WC> =

    SOLUTION_HERE()
//end::init1[]

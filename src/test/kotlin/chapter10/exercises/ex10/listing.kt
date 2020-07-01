package chapter10.exercises.ex10

import chapter10.Monoid

sealed class WC
data class Stub(val chars: String) : WC()
data class Part(val ls: String, val words: Int, val rs: String) : WC()

class Ex10 {
    //tag::init1[]
    val wcMonoid: Monoid<WC> = TODO()
    //end::init1[]
}

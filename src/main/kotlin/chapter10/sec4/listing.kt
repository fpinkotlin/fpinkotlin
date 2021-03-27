package chapter10.sec4

import chapter10.sec1.stringMonoid

//tag::init1[]
sealed class WC

data class Stub(val chars: String) : WC() //<1>
data class Part(val ls: String, val words: Int, val rs: String) : WC()//<2>
//end::init1[]

val sidebar = {
    //tag::init2[]
    "foo".length + "bar".length == ("foo" + "bar").length
    //end::init2[]

    val M = stringMonoid
    val N = stringMonoid
    val f = { a: String -> a }
    val x = ""
    val y = ""

    //tag::init3[]
    M.combine(f(x), f(y)) == f(N.combine(x, y))
    //end::init3[]
}

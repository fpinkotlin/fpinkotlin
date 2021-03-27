package chapter13.sec3_2

import chapter13.sec3_1.IO
import chapter13.sec3_1.Return
import chapter13.sec3_1.Suspend

object Listing {

    //tag::init1[]
    val f = { x: Int -> x }
    val g = List(100000) { idx -> f } // <1>
        .fold(f) { ff, h -> { n: Int -> ff(h(n)) } } // <2>
    //end::init1[]

    val result = g(42)
}

object Listing2 {
    //tag::init2[]
    val f = { x: Int -> Return(x) }
    val g = List(100000) { idx -> f } // <1>
        .fold(f) { a: (Int) -> IO<Int>, b: (Int) -> IO<Int> ->
            { x: Int ->
                Suspend { Unit }.flatMap { _ -> a(x).flatMap(b) } // <2>
            }
        }
    //end::init2[]
}

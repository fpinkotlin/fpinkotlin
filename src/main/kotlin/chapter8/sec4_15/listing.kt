package chapter8.sec4_15

import chapter7.sec4.Par
import chapter7.sec4.map
import chapter7.sec4.unit
import chapter8.Gen
import chapter8.sec4_12.forAllPar
import chapter8.sec4_9.equal

val listing = {
    val x = 1
    val f = { a: Int -> a + 1 }
    //tag::init1[]
    map(unit(x), f) == unit(f(x))
    //end::init1[]

    val y = unit(x)
    val id = { a: Int -> a }
    //tag::init2[]
    map(y, id) == y
    //end::init2[]
}

val listing2 = {
    //tag::init3[]
    val pint: Gen<Par<Int>> =
        Gen.choose(0, 10).map {
            unit(it)
        }

    val p = forAllPar(pint) { n ->
        equal(map(n) { it }, n)
    }
    //end::init3[]
}

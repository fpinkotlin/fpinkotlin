package chapter8.solutions.ex16

import arrow.core.extensions.list.foldable.forAll
import chapter7.sec4.Par
import chapter7.sec4.fork
import chapter7.sec4.unit
import chapter8.Gen
import chapter8.sec4_10.forAllPar
import chapter8.sec4_9.equal

val listing = {

    val pint: Gen<Par<Int>> =
        Gen.choose(0, 10)
            .map { unit(it) }

    //tag::init[]
    forAllPar(pint) { x ->
        equal(fork { x }, x)
    }
    //end::init[]

    val f = { i: Int -> i < 3 }
    listOf(1, 2, 3).takeWhile(f).forAll(f)
}

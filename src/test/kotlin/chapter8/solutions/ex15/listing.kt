package chapter8.solutions.ex15

import arrow.core.extensions.list.foldable.foldLeft
import chapter7.sec4.Par
import chapter7.sec4.fork
import chapter7.sec4.unit
import chapter8.Gen
import chapter8.sec4_9.map2

//tag::init[]
val pint2: Gen<Par<Int>> =
    Gen.choose(0, 20).flatMap { n ->
        Gen.listOfN(n, Gen.choose(-100, 100)).map { ls ->
            ls.foldLeft(unit(0)) { pint, i ->
                fork {
                    map2(pint, unit(i)) { a, b ->
                        a + b
                    }
                }
            }
        }
    }
//end::init[]

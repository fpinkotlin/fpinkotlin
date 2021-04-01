package chapter6.solutions.ex6

import chapter6.RNG
import chapter6.Rand
import chapter6.rng1
import chapter6.unit
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

//tag::init[]
fun <A, B, C> map2(ra: Rand<A>, rb: Rand<B>, f: (A, B) -> C): Rand<C> =
    { r1: RNG ->
        val (a, r2) = ra(r1)
        val (b, r3) = rb(r2)
        f(a, b) to r3
    }
//end::init[]

class Solution6 : WordSpec({
    "map2" should {

        "combine the results of two actions" {

            val combined: Rand<String> =
                map2(unit(1.0), unit(1), { d, i ->
                    ">>> $d double; $i int"
                })

            combined(rng1).first shouldBe ">>> 1.0 double; 1 int"
        }
    }
})

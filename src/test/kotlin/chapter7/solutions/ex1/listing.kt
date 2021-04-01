package chapter7.solutions.ex1

import io.kotlintest.specs.WordSpec

class Par<A>(val get: A)

fun <A> unit(a: () -> A): Par<A> = Par(a())

//tag::init[]
fun <A, B, C> map2(
    sum: Par<A>,
    sum1: Par<B>,
    function: (A, B) -> C
): Par<C> = Par(function(sum.get, sum1.get))
//end::init[]

class Solution1 : WordSpec({
    "Par.map2" should {
        """declare a valid signature that combines two Pars by
            applying another function""" {
                map2(unit { 1 }, unit { 2 }) { i, j -> i + j }
            }
    }
})

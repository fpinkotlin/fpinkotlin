package chapter7.solutions.sol1

import io.kotlintest.specs.WordSpec

class Par<A>(val get: A)

fun <A> unit(a: () -> A): Par<A> = Par(a())

fun <A> map2(
    sum: Par<A>,
    sum1: Par<A>,
    function: (A, A) -> A
): Par<A> = TODO()

class Solution_7_1 : WordSpec({
    "Par.map2" should {
        "declare a valid signature that combines two Pars by applying another function" {
            map2(unit { 1 }, unit { 2 }) { i, j -> i + j }
        }
    }
})

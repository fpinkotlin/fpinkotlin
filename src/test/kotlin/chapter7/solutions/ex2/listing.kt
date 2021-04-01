package chapter7.solutions.ex2

import io.kotlintest.specs.WordSpec

//tag::init[]
class Par<A>(val get: A) {
    companion object {

        fun <A> unit(a: A): Par<A> = Par(a)

        fun <A, B, C> map2(
            a1: Par<A>,
            a2: Par<B>,
            f: (A, B) -> C
        ): Par<C> = Par(f(a1.get, a2.get))

        fun <A> fork(f: () -> Par<A>): Par<A> = f()

        fun <A> lazyUnit(a: () -> A): Par<A> = Par(a())

        fun <A> run(a: Par<A>): A = a.get
    }
}
//end::init[]

class Solution2 : WordSpec({

    "Par" should {
        "create a computation that immediately results in a value" {
            Par.unit { 1 }
        }
        """combine the results of two parallel computations with
            a binary function""" {
                Par.map2(
                    Par.unit(1),
                    Par.unit(2)
                ) { i: Int, j: Int -> i + j }
            }
        "mark a computation for concurrent evaluation by run" {
            Par.fork { Par.unit { 1 } }
        }
        "wrap expression a for concurrent evaluation by run" {
            Par.lazyUnit { 1 }
        }
        """fully evaluate a given Par spawning computations
            and extracting value""" {
                Par.run(Par.unit { 1 })
            }
    }
})

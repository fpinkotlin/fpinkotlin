package chapter2.solutions.ex1

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import kotlinx.collections.immutable.persistentMapOf

class Solution1 : WordSpec({
    // tag::init[]
    fun fib(i: Int): Int {
        tailrec fun go(cnt: Int, curr: Int, nxt: Int): Int =
            if (cnt == 0)
                curr
            else go(cnt - 1, nxt, curr + nxt)
        return go(i, 0, 1)
    }
    // end::init[]

    "fib" should {
        "return the nth fibonacci number" {
            persistentMapOf(
                1 to 1,
                2 to 1,
                3 to 2,
                4 to 3,
                5 to 5,
                6 to 8,
                7 to 13,
                8 to 21
            ).forEach { (n, num) ->
                fib(n) shouldBe num
            }
        }
    }
})

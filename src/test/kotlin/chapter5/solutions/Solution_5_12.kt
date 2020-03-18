package chapter5.solutions

import chapter3.List
import chapter4.Some
import chapter5.Stream
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class Solution_5_12 : WordSpec({

    //tag::fibs[]
    fun fibs(): Stream<Int> =
        unfold(Pair(0, 1), { (curr, next) ->
            Some(Pair(curr, Pair(next, curr + next)))
        })
    //end::fibs[]

    //tag::from[]
    fun from(n: Int): Stream<Int> =
        unfold(n, { a -> Some(Pair(a, a + 1)) })
    //end::from[]

    //tag::constant[]
    fun <A> constant(n: A): Stream<A> =
        unfold(n, { a -> Some(Pair(a, a)) })
    //end::constant[]

    //tag::ones[]
    fun ones(): Stream<Int> =
        unfold(1, { Some(Pair(1, 1)) })
    //end::ones[]

    "fibs" should {
        "return a Stream of fibonacci sequence numbers" {
            fibs().take(10).toList() shouldBe
                List.of(0, 1, 1, 2, 3, 5, 8, 13, 21, 34)
        }
    }

    "from" should {
        "return a Stream of ever incrementing numbers" {
            from(5).take(5).toList() shouldBe
                List.of(5, 6, 7, 8, 9)
        }
    }

    "constants" should {
        "return an infinite stream of a given value" {
            constant(1).take(5).toList() shouldBe
                List.of(1, 1, 1, 1, 1)
        }
    }

    "ones" should {
        "return an infinite stream of 1s" {
            ones().take(5).toList() shouldBe
                List.of(1, 1, 1, 1, 1)
        }
    }
})

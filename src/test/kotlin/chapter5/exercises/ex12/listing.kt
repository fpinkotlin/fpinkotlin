package chapter5.exercises.ex12

import chapter3.List
import chapter5.Stream
import chapter5.toList
import chapter5.solutions.ex13.take
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import utils.SOLUTION_HERE

//TODO: Enable tests by removing `!` prefix
class Exercise12 : WordSpec({

    //tag::fibs[]
    fun fibs(): Stream<Int> =

        SOLUTION_HERE()
    //end::fibs[]

    //tag::from[]
    fun from(n: Int): Stream<Int> =

        SOLUTION_HERE()
    //end::from[]

    //tag::constant[]
    fun <A> constant(n: A): Stream<A> =

        SOLUTION_HERE()
    //end::constant[]

    //tag::ones[]
    fun ones(): Stream<Int> =

        SOLUTION_HERE()
    //end::ones[]

    "fibs" should {
        "!return a Stream of fibonacci sequence numbers" {
            fibs().take(10).toList() shouldBe
                List.of(0, 1, 1, 2, 3, 5, 8, 13, 21, 34)
        }
    }

    "from" should {
        "!return a Stream of ever incrementing numbers" {
            from(5).take(5).toList() shouldBe List.of(5, 6, 7, 8, 9)
        }
    }

    "constants" should {
        "!return an infinite stream of a given value" {
            constant(1).take(5).toList() shouldBe
                List.of(1, 1, 1, 1, 1)
        }
    }

    "ones" should {
        "!return an infinite stream of 1s" {
            ones().take(5).toList() shouldBe List.of(1, 1, 1, 1, 1)
        }
    }
})

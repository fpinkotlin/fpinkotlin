package chapter6.exercises.ex8

import chapter6.Rand
import chapter6.rng1
// import chapter6.solutions.ex1.nonNegativeInt
import chapter6.unit
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import utils.SOLUTION_HERE

//TODO: Enable tests by removing `!` prefix
class Exercise8 : WordSpec({

    //tag::init[]
    fun <A, B> flatMap(f: Rand<A>, g: (A) -> Rand<B>): Rand<B> =

        SOLUTION_HERE()
    //end::init[]

    fun nonNegativeIntLessThan(n: Int): Rand<Int> = SOLUTION_HERE()

    "flatMap" should {
        "!pass along an RNG" {

            val result =
                flatMap(
                    unit(1),
                    { i -> unit(i.toString()) })(rng1)

            result.first shouldBe "1"
            result.second shouldBe rng1
        }
    }

    "nonNegativeIntLessThan" should {
        "!return a non negative int less than n" {

            val result =
                nonNegativeIntLessThan(10)(rng1)

            result.first shouldBe 1
        }
    }
})

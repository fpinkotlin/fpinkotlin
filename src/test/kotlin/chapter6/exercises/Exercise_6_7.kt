package chapter6.exercises

import chapter3.List
import chapter6.RNG
import chapter6.Rand
import chapter6.rng1
import chapter6.unit
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

//tag::init[]
fun <A> sequence(fs: List<Rand<A>>): Rand<List<A>> = TODO()
//end::init[]

//tag::init2[]
fun <A> sequence2(fs: List<Rand<A>>): Rand<List<A>> = TODO()
//enc::init2[]

fun ints2(count: Int, rng: RNG): Pair<List<Int>, RNG> = TODO()

/**
 * TODO: Re-enable tests by removing `!` prefix!
 */
class Exercise_6_7 : WordSpec({
    "sequence" should {

        "!combine the results of many actions using recursion" {

            val combined: Rand<List<Int>> =
                sequence(
                    List.of(
                        unit(1),
                        unit(2),
                        unit(3),
                        unit(4)
                    )
                )

            combined(rng1).first shouldBe
                List.of(1, 2, 3, 4)
        }

        """!combine the results of many actions using
            foldRight and map2""" {

                val combined2: Rand<List<Int>> =
                    sequence2(
                        List.of(
                            unit(1),
                            unit(2),
                            unit(3),
                            unit(4)
                        )
                    )

                combined2(rng1).first shouldBe
                    List.of(1, 2, 3, 4)
            }
    }

    "ints" should {
        "!generate a list of ints of a specified length" {
            ints2(4, rng1).first shouldBe
                List.of(1, 1, 1, 1)
        }
    }
})

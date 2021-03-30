package chapter10.exercises.ex1

import chapter10.Monoid
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import utils.SOLUTION_HERE

//tag::init1[]
fun intAddition(): Monoid<Int> =

    SOLUTION_HERE()

fun intMultiplication(): Monoid<Int> =

    SOLUTION_HERE()

fun booleanOr(): Monoid<Boolean> =

    SOLUTION_HERE()

fun booleanAnd(): Monoid<Boolean> =

    SOLUTION_HERE()
//end::init1[]

//TODO: Enable tests by removing `!` prefix
class Exercise1 : WordSpec({
    "int and boolean monoids" should {
        "!uphold the law of associativity" {
            assertAll<Int, Int> { a, b ->
                assert(intAddition().combine(a, b) == (a + b))
                assert(intMultiplication().combine(a, b) == (a * b))
            }
            assertAll<Boolean, Boolean> { a, b ->
                assert(booleanAnd().combine(a, b) == (a && b))
                assert(booleanOr().combine(a, b) == (a || b))
            }
        }
        "!uphold the law of identity" {
            intAddition().nil shouldBe 0
            intMultiplication().nil shouldBe 1
            booleanAnd().nil shouldBe true
            booleanOr().nil shouldBe false
        }
    }
})

package chapter10.exercises.ex1

import chapter10.Monoid
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

//tag::init1[]
val intAddition: Monoid<Int> = TODO()

val intMultiplication: Monoid<Int> = TODO()

val booleanOr: Monoid<Boolean> = TODO()

val booleanAnd: Monoid<Boolean> = TODO()
//end::init1[]

class Exercise1 : WordSpec({
    "int and boolean monoids" should {
        "!uphold the law of associativity" {
            assertAll<Int, Int> { a, b ->
                assert(intAddition.combine(a, b) == (a + b))
                assert(intMultiplication.combine(a, b) == (a * b))
            }
            assertAll<Boolean, Boolean> { a, b ->
                assert(booleanAnd.combine(a, b) == (a && b))
                assert(booleanOr.combine(a, b) == (a || b))
            }
        }
        "!uphold the law of identity" {
            intAddition.nil shouldBe 0
            intMultiplication.nil shouldBe 1
            booleanAnd.nil shouldBe true
            booleanOr.nil shouldBe false
        }
    }
})

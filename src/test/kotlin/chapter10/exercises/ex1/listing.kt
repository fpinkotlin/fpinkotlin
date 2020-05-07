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
                assert(intAddition.op(a, b) == (a + b))
                assert(intMultiplication.op(a, b) == (a * b))
            }
            assertAll<Boolean, Boolean> { a, b ->
                assert(booleanAnd.op(a, b) == (a && b))
                assert(booleanOr.op(a, b) == (a || b))
            }
        }
        "!uphold the law of identity" {
            intAddition.zero shouldBe 0
            intMultiplication.zero shouldBe 1
            booleanAnd.zero shouldBe true
            booleanOr.zero shouldBe false
        }
    }
})

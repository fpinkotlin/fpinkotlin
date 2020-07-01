package chapter10.exercises.ex1

import chapter10.Monoid
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class Ex1 {
    //tag::init1[]
    val intAddition: Monoid<Int> = TODO()

    val intMultiplication: Monoid<Int> = TODO()

    val booleanOr: Monoid<Boolean> = TODO()

    val booleanAnd: Monoid<Boolean> = TODO()
//end::init1[]
}

class Exercise1 : WordSpec({
    "int and boolean monoids" should {
        "!uphold the law of associativity" {
            assertAll<Int, Int> { a, b ->
                assert(Ex1().intAddition.combine(a, b) == (a + b))
                assert(Ex1().intMultiplication.combine(a, b) == (a * b))
            }
            assertAll<Boolean, Boolean> { a, b ->
                assert(Ex1().booleanAnd.combine(a, b) == (a && b))
                assert(Ex1().booleanOr.combine(a, b) == (a || b))
            }
        }
        "!uphold the law of identity" {
            Ex1().intAddition.nil shouldBe 0
            Ex1().intMultiplication.nil shouldBe 1
            Ex1().booleanAnd.nil shouldBe true
            Ex1().booleanOr.nil shouldBe false
        }
    }
})

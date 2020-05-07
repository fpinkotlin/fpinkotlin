package chapter10.solutions.ex1

import chapter10.Monoid
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

//tag::init1[]
val intAddition: Monoid<Int> = object : Monoid<Int> {

    override fun op(a1: Int, a2: Int): Int = a1 + a2

    override val zero: Int = 0
}

val intMultiplication: Monoid<Int> = object : Monoid<Int> {

    override fun op(a1: Int, a2: Int): Int = a1 * a2

    override val zero: Int = 1
}

val booleanOr: Monoid<Boolean> = object : Monoid<Boolean> {

    override fun op(a1: Boolean, a2: Boolean): Boolean = a1 || a2

    override val zero: Boolean = false
}

val booleanAnd: Monoid<Boolean> = object : Monoid<Boolean> {

    override fun op(a1: Boolean, a2: Boolean): Boolean = a1 && a2

    override val zero: Boolean = true
}
//end::init1[]

class Exercise1 : WordSpec({
    "int and boolean monoids" should {
        "uphold the law of associativity" {
            assertAll<Int, Int> { a, b ->
                assert(intAddition.op(a, b) == (a + b))
                assert(intMultiplication.op(a, b) == (a * b))
            }
            assertAll<Boolean, Boolean> { a, b ->
                assert(booleanAnd.op(a, b) == (a && b))
                assert(booleanOr.op(a, b) == (a || b))
            }
        }
        "uphold the law of identity" {
            intAddition.zero shouldBe 0
            intMultiplication.zero shouldBe 1
            booleanAnd.zero shouldBe true
            booleanOr.zero shouldBe false
        }
    }
})

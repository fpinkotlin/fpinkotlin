package chapter10.solutions.ex1

import chapter10.Monoid
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

//tag::init1[]
fun intAddition(): Monoid<Int> = object : Monoid<Int> {

    override fun combine(a1: Int, a2: Int): Int = a1 + a2

    override val nil: Int = 0
}

fun intMultiplication(): Monoid<Int> = object : Monoid<Int> {

    override fun combine(a1: Int, a2: Int): Int = a1 * a2

    override val nil: Int = 1
}

fun booleanOr(): Monoid<Boolean> = object : Monoid<Boolean> {

    override fun combine(a1: Boolean, a2: Boolean): Boolean = a1 || a2

    override val nil: Boolean = false
}

fun booleanAnd(): Monoid<Boolean> = object : Monoid<Boolean> {

    override fun combine(a1: Boolean, a2: Boolean): Boolean = a1 && a2

    override val nil: Boolean = true
}
//end::init1[]

class Exercise1 : WordSpec({
    "int and boolean monoids" should {
        "uphold the law of associativity" {
            assertAll<Int, Int> { a, b ->
                assert(intAddition().combine(a, b) == (a + b))
                assert(intMultiplication().combine(a, b) == (a * b))
            }
            assertAll<Boolean, Boolean> { a, b ->
                assert(booleanAnd().combine(a, b) == (a && b))
                assert(booleanOr().combine(a, b) == (a || b))
            }
        }
        "uphold the law of identity" {
            intAddition().nil shouldBe 0
            intMultiplication().nil shouldBe 1
            booleanAnd().nil shouldBe true
            booleanOr().nil shouldBe false
        }
    }
})

package chapter10.solutions.ex18

import chapter10.Monoid
import chapter10.stringMonoid
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

//tag::init1[]
fun <A, B> functionMonoid(b: Monoid<B>): Monoid<(A) -> B> =
    object : Monoid<(A) -> B> {
        override fun combine(f: (A) -> B, g: (A) -> B): (A) -> B =
            { a: A -> b.combine(f(a), g(a)) }

        override val nil: (A) -> B =
            { a -> b.nil }
    }
//end::init1[]

class Exercise18 : WordSpec({

    val fm = functionMonoid<Int, String>(stringMonoid)

    "functionMonoid" should {
        "combine the results of two functions using another monoid" {
            assertAll<Int> { i ->
                fm.combine(
                    { a -> "x$a" },
                    { a -> "y$a" })(i) shouldBe "x${i}y$i"
            }
        }
    }
})

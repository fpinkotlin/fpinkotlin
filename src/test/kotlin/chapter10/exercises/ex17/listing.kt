package chapter10.exercises.ex17

import chapter10.Monoid
import chapter10.intAdditionMonoid
import chapter10.stringMonoid
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

//tag::init1[]
fun <A, B> productMonoid(
    ma: Monoid<A>,
    mb: Monoid<B>
): Monoid<Pair<A, B>> = TODO()
//end::init1[]

class Exercise17 : WordSpec({
    "productMonoid" should {
        "!comply with the law of associativity" {
            assertAll<Pair<Int, String>> { p ->
                val product =
                    productMonoid(intAdditionMonoid, stringMonoid).op(p, p)
                product.first shouldBe intAdditionMonoid.op(
                    p.first,
                    p.first
                )
                product.second shouldBe stringMonoid.op(p.second, p.second)
            }
        }
    }
})

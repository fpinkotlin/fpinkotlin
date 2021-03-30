package chapter10.exercises.ex17

import chapter10.Monoid
import chapter10.intAdditionMonoid
import chapter10.stringMonoid
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import utils.SOLUTION_HERE

//tag::init1[]
fun <A, B> productMonoid(
    ma: Monoid<A>,
    mb: Monoid<B>
): Monoid<Pair<A, B>> =

    SOLUTION_HERE()
//end::init1[]

//TODO: Enable tests by removing `!` prefix
class Exercise17 : WordSpec({
    "productMonoid" should {
        "!comply with the law of associativity" {
            assertAll<Pair<Int, String>> { p ->
                val product =
                    productMonoid(intAdditionMonoid, stringMonoid)
                        .combine(p, p)
                product.first shouldBe intAdditionMonoid.combine(
                    p.first,
                    p.first
                )
                product.second shouldBe stringMonoid.combine(
                    p.second,
                    p.second
                )
            }
        }
    }
})

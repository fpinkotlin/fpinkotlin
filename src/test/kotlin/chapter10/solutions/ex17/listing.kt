package chapter10.solutions.ex17

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
): Monoid<Pair<A, B>> =
    object : Monoid<Pair<A, B>> {
        override fun combine(a1: Pair<A, B>, a2: Pair<A, B>): Pair<A, B> =
            ma.combine(a1.first, a2.first) to
                mb.combine(a1.second, a2.second)

        override val nil: Pair<A, B>
            get() = ma.nil to mb.nil
    }
//end::init1[]

class Exercise17 : WordSpec({
    "productMonoid" should {
        "comply with the law of associativity" {
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

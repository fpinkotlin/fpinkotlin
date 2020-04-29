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
        override fun op(a1: Pair<A, B>, a2: Pair<A, B>): Pair<A, B> =
            Pair(ma.op(a1.first, a2.first), mb.op(a1.second, a2.second))

        override val zero: Pair<A, B>
            get() = Pair(ma.zero, mb.zero)
    }
//end::init1[]

class Exercise17 : WordSpec({
    "productMonoid" should {
        "comply with the law of associativity" {
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

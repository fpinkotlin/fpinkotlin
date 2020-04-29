package chapter10.solutions.ex4

import chapter10.Monoid
import chapter10.intAdditionMonoid
import chapter10.intMultiplicationMonoid
import chapter8.Passed
import chapter8.SimpleRNG
import chapter8.sec3.listing3.Gen
import chapter8.sec3.listing3.Prop.Companion.forAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

//tag::init1[]
fun <A> monoidLaws(m: Monoid<A>, gen: Gen<A>) =
    forAll(
        gen.flatMap { a ->
            gen.flatMap { b ->
                gen.map { c ->
                    Triple(a, b, c)
                }
            }
        }
    ) { (a, b, c) ->
        m.op(a, m.op(b, c)) == m.op(m.op(a, b), c) &&
            m.op(m.zero, a) == m.op(a, m.zero)
    }
//end::init1[]

//tag::init2[]
class Exercise4 : WordSpec({
    val max = 100
    val count = 100
    val rng = SimpleRNG(42)
    val intGen = Gen.choose(-10000, 10000)

    "law of associativity" should {
        "be upheld using existing monoids" {
            monoidLaws(intAdditionMonoid, intGen)
                .check(max, count, rng) shouldBe Passed

            monoidLaws(intMultiplicationMonoid, intGen)
                .check(max, count, rng) shouldBe Passed
        }
    }
})
//end::init2[]

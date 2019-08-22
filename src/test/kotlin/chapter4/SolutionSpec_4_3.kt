package chapter4

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

fun <A, B, C> map2(a: Option<A>, b: Option<B>, f: (A, B) -> C): Option<C> =
        a.flatMap { aa -> b.map { bb -> f(aa, bb) } }

class SolutionSpec_4_3 : WordSpec({

    "map2" should {

        val a = Some(5)
        val b = Some(20)
        val none = Option.empty<Int>()

        "combine two option values using a binary function" {
            map2(a, b) { aa, bb -> aa * bb } shouldBe Some(100)
        }

        "return none if either option is not defined" {
            map2(a, none) { aa, bb -> aa * bb } shouldBe None
            map2(none, b) { aa, bb -> aa * bb } shouldBe None
        }
    }
})
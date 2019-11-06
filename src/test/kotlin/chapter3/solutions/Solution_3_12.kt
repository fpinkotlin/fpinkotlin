package chapter3.solutions

import chapter3.List
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

// tag::init[]
fun <A, B> foldLeftR(xs: List<A>, z: B, f: (B, A) -> B): B =
    (foldRight(xs, { b: B -> b }, { a, g -> { b -> g(f(b, a)) } }))(z)

fun <A, B> foldRightL(xs: List<A>, z: B, f: (A, B) -> B): B =
    foldLeft(xs, { b: B -> b }, { g, a -> { b -> g(f(a, b)) } })(z)

//expanded example
typealias Identity<B> = (B) -> B

fun <A, B> foldLeftRLikeYouMeanIt(
    ls: List<A>,
    outerIdentity: B,
    combiner: (B, A) -> B
): B {

    val innerIdentity: Identity<B> = { b: B -> b }

    val combinerDelayer: (A, Identity<B>) -> Identity<B> =
        { a: A, delayExec: Identity<B> ->
            { b: B ->
                delayExec(combiner(b, a))
            }
        }

    fun go(combinerDelayer: (A, Identity<B>) -> Identity<B>): Identity<B> =
        foldRight(ls, innerIdentity, combinerDelayer)

    return go(combinerDelayer).invoke(outerIdentity)
}
// end::init[]

class Solution_3_12 : WordSpec({
    "list foldLeftR" should {
        "implement foldLeft functionality using foldRight" {
            foldLeftR(List.of(1, 2, 3, 4, 5), 0, { x, y -> x + y }) shouldBe 15
        }
    }

    "list foldRightL" should {
        "implement foldRight functionality using foldLeft" {
            foldRightL(List.of(1, 2, 3, 4, 5), 0, { x, y -> x + y }) shouldBe 15
        }
    }
})
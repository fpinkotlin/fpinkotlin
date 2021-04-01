package chapter2.solutions.ex2

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import kotlinx.collections.immutable.persistentListOf

// tag::init[]
val <T> List<T>.tail: List<T>
    get() = drop(1)

val <T> List<T>.head: T
    get() = first()

fun <A> isSorted(aa: List<A>, order: (A, A) -> Boolean): Boolean {
    tailrec fun go(x: A, xs: List<A>): Boolean =
        if (xs.isEmpty()) true
        else if (!order(x, xs.head)) false
        else go(xs.head, xs.tail)

    return aa.isEmpty() || go(aa.head, aa.tail)
}
// end::init[]

class Solution2 : WordSpec({
    "isSorted" should {
        """detect ordering of a list of correctly ordered Ints based
            on an ordering HOF""" {
                isSorted(
                    persistentListOf(1, 2, 3)
                ) { a, b -> b > a } shouldBe true
            }
        """detect ordering of a list of incorrectly ordered Ints
            based on an ordering HOF""" {
                isSorted(
                    persistentListOf(1, 3, 2)
                ) { a, b -> b > a } shouldBe false
            }
        """verify ordering of a list of correctly ordered Strings
            based on an ordering HOF""" {
                isSorted(
                    persistentListOf("a", "b", "c")
                ) { a, b -> b > a } shouldBe true
            }
        """verify ordering of a list of incorrectly ordered Strings
            based on an ordering HOF""" {
                isSorted(
                    persistentListOf("a", "z", "w")
                ) { a, b -> b > a } shouldBe false
            }
        "return true for an empty list" {
            isSorted(persistentListOf<Int>()) { a, b ->
                b > a
            } shouldBe true
        }
    }
})

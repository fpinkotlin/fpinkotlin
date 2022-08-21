package chapter3.solutions.sol9

import chapter3.Cons
import chapter3.List
import chapter3.Nil
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotThrow
import io.kotlintest.specs.WordSpec

// tag::init[]
tailrec fun <A, B> foldLeft(xs: List<A>, z: B, f: (B, A) -> B): B =
    when (xs) {
        is Nil -> z
        is Cons -> foldLeft(xs.tail, f(z, xs.head), f)
    }
// end::init[]

class Solution9 : WordSpec({
    "list foldLeft" should {
        """apply a function f providing a zero accumulator from tail
            recursive position""" {
                foldLeft(
                    List.of(1, 2, 3, 4, 5),
                    0,
                    { x, y -> x + y }) shouldBe 15
            }
    }

    "fold left with large array" should {
        "not throw a stackoverflow" {
            val ints = (1..10000).toList().toTypedArray()
            shouldNotThrow<StackOverflowError> {
                foldLeft(
                    List.of(*ints),
                    0,
                    {x, y -> x + y }
                )
            }
        }
    }
})

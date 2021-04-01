package chapter5.solutions.ex1

import chapter3.List
import chapter3.reverse
import chapter5.Cons
import chapter5.Empty
import chapter5.Stream
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import chapter3.Cons as ConsL
import chapter3.Nil as NilL

//tag::init[]
//Unsafe! Naive solution could cause a stack overflow.
fun <A> Stream<A>.toListUnsafe(): List<A> = when (this) {
    is Empty -> NilL
    is Cons -> ConsL(this.head(), this.tail().toListUnsafe())
}

//Use tailrec in combination with reverse for a safer implementation
fun <A> Stream<A>.toList(): List<A> {
    tailrec fun go(xs: Stream<A>, acc: List<A>): List<A> = when (xs) {
        is Empty -> acc
        is Cons -> go(xs.tail(), ConsL(xs.head(), acc))
    }
    return reverse(go(this, NilL))
}
//end::init[]

class Solution1 : WordSpec({
    "Stream.toList" should {
        "force the stream into an evaluated list" {
            val s = Stream.of(1, 2, 3, 4, 5)
            s.toListUnsafe() shouldBe List.of(1, 2, 3, 4, 5)
            s.toList() shouldBe List.of(1, 2, 3, 4, 5)
        }
    }
})

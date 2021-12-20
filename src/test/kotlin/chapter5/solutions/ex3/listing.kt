package chapter5.solutions.ex3

import chapter3.List
import chapter5.Cons
import chapter5.Empty
import chapter5.Stream
import chapter5.Stream.Companion.cons
import chapter5.Stream.Companion.empty
import chapter5.toList
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class Solution3 : WordSpec({

    //tag::init[]
    fun <A> Stream<A>.takeWhile(p: (A) -> Boolean): Stream<A> =
        when (this) {
            is Empty -> empty()
            is Cons ->
                if (p(this.head()))
                    cons(this.head, { this.tail().takeWhile(p) })
                else empty()
        }
    //end::init[]

    "Stream.takeWhile" should {
        "return elements while the predicate evaluates true" {
            val s = Stream.of(1, 2, 3, 4, 5)
            s.takeWhile { it < 4 }.toList() shouldBe
                List.of(1, 2, 3)
        }
        "stop returning once predicate evaluates false" {
            val s = Stream.of(1, 2, 3, 4, 5, 4, 3, 2, 1)
            s.takeWhile { it < 4 }.toList() shouldBe
                List.of(1, 2, 3)
        }
        "return all elements if predicate always evaluates true" {
            val s = Stream.of(1, 2, 3, 4, 5)
            s.takeWhile { true }.toList() shouldBe
                List.of(1, 2, 3, 4, 5)
        }
        "return empty if predicate always evaluates false" {
            val s = Stream.of(1, 2, 3, 4, 5)
            s.takeWhile { false }.toList() shouldBe
                List.empty()
        }
    }
})

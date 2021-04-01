package chapter5.solutions.ex2

import chapter3.List
import chapter3.Nil
import chapter5.Cons
import chapter5.Empty
import chapter5.Stream
import chapter5.Stream.Companion.cons
import chapter5.Stream.Companion.empty
import chapter5.toList
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class Solution2 : WordSpec({

    //tag::take[]
    fun <A> Stream<A>.take(n: Int): Stream<A> {
        fun go(xs: Stream<A>, n: Int): Stream<A> = when (xs) {
            is Empty -> empty()
            is Cons ->
                if (n == 0) empty()
                else cons(xs.head, { go(xs.tail(), n - 1) })
        }
        return go(this, n)
    }
    //end::take[]

    //tag::drop[]
    fun <A> Stream<A>.drop(n: Int): Stream<A> {
        tailrec fun go(xs: Stream<A>, n: Int): Stream<A> = when (xs) {
            is Empty -> empty()
            is Cons ->
                if (n == 0) xs
                else go(xs.tail(), n - 1)
        }
        return go(this, n)
    }
    //end::drop[]

    "Stream.take(n)" should {
        "return the first n elements of a stream" {
            val s = Stream.of(1, 2, 3, 4, 5)
            s.take(3).toList() shouldBe List.of(1, 2, 3)
        }

        "return all the elements if the stream is exhausted" {
            val s = Stream.of(1, 2, 3)
            s.take(5).toList() shouldBe List.of(1, 2, 3)
        }

        "return an empty stream if the stream is empty" {
            val s = Stream.empty<Int>()
            s.take(3).toList() shouldBe Nil
        }
    }

    "Stream.drop(n)" should {
        "return the remaining elements of a stream" {
            val s = Stream.of(1, 2, 3, 4, 5)
            s.drop(3).toList() shouldBe List.of(4, 5)
        }

        "return empty if the stream is exhausted" {
            val s = Stream.of(1, 2, 3)
            s.drop(5).toList() shouldBe Nil
        }

        "return empty if the stream is empty" {
            val s = Stream.empty<Int>()
            s.drop(5).toList() shouldBe Nil
        }
    }
})

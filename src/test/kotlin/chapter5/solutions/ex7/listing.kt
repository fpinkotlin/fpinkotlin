package chapter5.solutions.ex7

import chapter3.List
import chapter5.Stream
import chapter5.Stream.Companion.cons
import chapter5.Stream.Companion.empty
import chapter5.toList
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

//tag::append[]
fun <A> Stream<A>.append(sa: () -> Stream<A>): Stream<A> =
    foldRight(sa) { h, t -> cons({ h }, t) }
//end::append[]

class Solution7 : WordSpec({

    //tag::map[]
    fun <A, B> Stream<A>.map(f: (A) -> B): Stream<B> =
        this.foldRight(
            { empty<B>() },
            { h, t -> cons({ f(h) }, t) })
    //end::map[]

    //tag::filter[]
    fun <A> Stream<A>.filter(f: (A) -> Boolean): Stream<A> =
        this.foldRight(
            { empty<A>() },
            { h, t -> if (f(h)) cons({ h }, t) else t() })
    //end::filter[]

    //tag::flatmap[]
    fun <A, B> Stream<A>.flatMap(f: (A) -> Stream<B>): Stream<B> =
        foldRight(
            { empty<B>() },
            { h, t -> f(h).append(t) })
    //end::flatmap[]

    "Stream.map" should {
        "apply a function to each evaluated element in a stream" {
            val s = Stream.of(1, 2, 3, 4, 5)
            s.map { (it * 2).toString() }.toList() shouldBe
                List.of("2", "4", "6", "8", "10")
        }
        "return an empty stream if no elements are found" {
            empty<Int>().map { (it * 2).toString() } shouldBe empty()
        }
    }

    "Stream.filter" should {
        """return all elements of a stream that conform
            to a predicate""" {
            val s = Stream.of(1, 2, 3, 4, 5)
            s.filter { it % 2 == 0 }.toList() shouldBe
                List.of(2, 4)
        }
        "return no elements of an empty stream" {
            empty<Int>().filter { it % 2 == 0 }
                .toList() shouldBe List.empty()
        }
    }
    "Stream.append" should {
        "append two streams to each other" {
            val s1 = Stream.of(1, 2, 3)
            val s2 = Stream.of(4, 5, 6)
            s1.append { s2 }.toList() shouldBe
                List.of(1, 2, 3, 4, 5, 6)
        }
        "append a stream to an empty stream" {
            val s1 = empty<Int>()
            val s2 = Stream.of(1, 2, 3)
            s1.append { s2 }.toList() shouldBe List.of(1, 2, 3)
        }
        "append an empty stream to a stream" {
            val s1 = Stream.of(1, 2, 3)
            val s2 = empty<Int>()
            s1.append { s2 }.toList() shouldBe List.of(1, 2, 3)
        }
    }
    "Stream.flatMap" should {
        """apply a function that can fail to each evaluated
            element in a stream""" {
            val s = Stream.of(1, 2, 3, 4, 5)
            s.flatMap { Stream.of("$it", "${it * 2}") }
                .toList() shouldBe
                List.of(
                    "1",
                    "2",
                    "2",
                    "4",
                    "3",
                    "6",
                    "4",
                    "8",
                    "5",
                    "10"
                )
        }
    }
})

package chapter5.solutions.ex13

import chapter3.List
import chapter3.Nil
import chapter4.None
import chapter4.Option
import chapter4.Some
import chapter5.Cons
import chapter5.Stream
import chapter5.toList
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

//tag::map[]
fun <A, B> Stream<A>.map(f: (A) -> B): Stream<B> =
    Stream.unfold(this) { s: Stream<A> ->
        when (s) {
            is Cons -> Some(f(s.head()) to s.tail())
            else -> None
        }
    }
//end::map[]

//tag::take[]
fun <A> Stream<A>.take(n: Int): Stream<A> =
    Stream.unfold(this) { s: Stream<A> ->
        when (s) {
            is Cons ->
                if (n > 0)
                    Some(s.head() to s.tail().take(n - 1))
                else None
            else -> None
        }
    }
//end::take[]

//tag::takewhile[]
fun <A> Stream<A>.takeWhile(p: (A) -> Boolean): Stream<A> =
    Stream.unfold(this,
        { s: Stream<A> ->
            when (s) {
                is Cons ->
                    if (p(s.head()))
                        Some(s.head() to s.tail())
                    else None
                else -> None
            }
        })
//end::takewhile[]

//tag::zipwith[]
fun <A, B, C> Stream<A>.zipWith(
    that: Stream<B>,
    f: (A, B) -> C
): Stream<C> =
    Stream.unfold(this to that) { (ths: Stream<A>, tht: Stream<B>) ->
        when (ths) {
            is Cons ->
                when (tht) {
                    is Cons ->
                        Some(
                            Pair(
                                f(ths.head(), tht.head()),
                                ths.tail() to tht.tail()
                            )
                        )
                    else -> None
                }
            else -> None
        }
    }
//end::zipwith[]

//tag::zipall[]
fun <A, B> Stream<A>.zipAll(
    that: Stream<B>
): Stream<Pair<Option<A>, Option<B>>> =
    Stream.unfold(this to that) { (ths, tht) ->
        when (ths) {
            is Cons -> when (tht) {
                is Cons ->
                    Some(
                        Pair(
                            Some(ths.head()) to Some(tht.head()),
                            ths.tail() to tht.tail()
                        )
                    )
                else ->
                    Some(
                        Pair(
                            Some(ths.head()) to None,
                            ths.tail() to Stream.empty<B>()
                        )
                    )
            }
            else -> when (tht) {
                is Cons ->
                    Some(
                        Pair(
                            None to Some(tht.head()),
                            Stream.empty<A>() to tht.tail()
                        )
                    )
                else -> None
            }
        }
    }
//end::zipall[]

class Solution13 : WordSpec({

    "Stream.map" should {
        "apply a function to each evaluated element in a stream" {
            val s = Stream.of(1, 2, 3, 4, 5)
            s.map { "${(it * 2)}" }.toList() shouldBe
                List.of("2", "4", "6", "8", "10")
        }
        "return an empty stream if no elements are found" {
            Stream.empty<Int>()
                .map { (it * 2).toString() } shouldBe Stream.empty()
        }
    }

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

    "Stream.takeWhile" should {
        "return elements while the predicate evaluates true" {
            val s = Stream.of(1, 2, 3, 4, 5)
            s.takeWhile { it < 4 }.toList() shouldBe List.of(1, 2, 3)
        }
        "return all elements if predicate always evaluates true" {
            val s = Stream.of(1, 2, 3, 4, 5)
            s.takeWhile { true }.toList() shouldBe
                List.of(1, 2, 3, 4, 5)
        }
        "return empty if predicate always evaluates false" {
            val s = Stream.of(1, 2, 3, 4, 5)
            s.takeWhile { false }.toList() shouldBe List.empty()
        }
    }

    "Stream.zipWith" should {
        "apply a function to elements of two corresponding lists" {
            Stream.of(1, 2, 3).zipWith(
                Stream.of(4, 5, 6)
            ) { x, y ->
                x + y
            }.toList() shouldBe List.of(5, 7, 9)
        }
    }

    "Stream.zipAll" should {
        "combine two streams of equal length" {
            Stream.of(1, 2, 3).zipAll(Stream.of(1, 2, 3))
                .toList() shouldBe List.of(
                Some(1) to Some(1),
                Some(2) to Some(2),
                Some(3) to Some(3)
            )
        }
        "combine two streams until the first is exhausted" {
            Stream.of(1, 2, 3, 4).zipAll(Stream.of(1, 2, 3))
                .toList() shouldBe List.of(
                Some(1) to Some(1),
                Some(2) to Some(2),
                Some(3) to Some(3),
                Some(4) to None
            )
        }
        "combine two streams until the second is exhausted" {
            Stream.of(1, 2, 3).zipAll(Stream.of(1, 2, 3, 4))
                .toList() shouldBe List.of(
                Some(1) to Some(1),
                Some(2) to Some(2),
                Some(3) to Some(3),
                None to Some(4)
            )
        }
    }
})

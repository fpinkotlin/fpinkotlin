package chapter5.solutions

import chapter3.List
import chapter4.None
import chapter4.Some
import chapter5.Cons
import chapter5.Stream
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import chapter3.Cons as ConsL
import chapter3.Nil as NilL

class Solution_5_15 : WordSpec({

    //tag::tails[]
    fun <A> Stream<A>.tails(): Stream<Stream<A>> =
        unfold(this) { s: Stream<A> ->
            when (s) {
                is Cons ->
                    Some(Pair(s, s.tail()))
                else -> None
            }
        }.append({ Stream.empty() })
    //end::tails[]

    fun <A, B> List<A>.map(f: (A) -> B): List<B> = when (this) {
        is ConsL -> ConsL(f(this.head), this.tail.map(f))
        is NilL -> NilL
    }

    "Stream.tails" should {
        "return the stream of suffixes of the input sequence" {
            Stream.of(1, 2, 3)
                .tails().toList().map { it.toList() } shouldBe
                List.of(
                    ConsL(1, ConsL(2, ConsL(3, NilL))),
                    ConsL(2, ConsL(3, NilL)),
                    ConsL(3, NilL),
                    NilL
                )
        }
    }
})

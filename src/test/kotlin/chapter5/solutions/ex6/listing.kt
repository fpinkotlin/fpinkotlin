package chapter5.solutions.ex6

import chapter4.None
import chapter4.Option
import chapter4.Some
import chapter5.Stream
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class Solution6 : WordSpec({

    //tag::init[]
    fun <A> Stream<A>.headOption(): Option<A> =
        this.foldRight(
            { Option.empty() },
            { a, _ -> Some(a) }
        )
    //end::init[]

    "Stream.headOption" should {
        """return some first element from the stream if it
            is not empty""" {
            val s = Stream.of(1, 2, 3, 4)
            s.headOption() shouldBe Some(1)
        }

        "return none if the stream is empty" {
            Stream.empty<Int>().headOption() shouldBe None
        }
    }
})

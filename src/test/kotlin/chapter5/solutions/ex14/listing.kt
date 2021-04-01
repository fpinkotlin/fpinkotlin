package chapter5.solutions.ex14

import chapter5.Stream
import chapter5.solutions.ex13.takeWhile
import chapter5.solutions.ex13.zipAll
import chapter5.solutions.ex4.forAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class Solution14 : WordSpec({

    //tag::startswith[]
    fun <A> Stream<A>.startsWith(that: Stream<A>): Boolean =
        this.zipAll(that)
            .takeWhile { !it.second.isEmpty() }
            .forAll { it.first == it.second }
    //end::startswith[]

    "Stream.startsWith" should {
        "detect if one stream is a prefix of another" {
            Stream.of(1, 2, 3)
                .startsWith(Stream.of(1, 2)) shouldBe true
        }
        "detect if one stream is not a prefix of another" {
            Stream.of(1, 2, 3)
                .startsWith(Stream.of(2, 3)) shouldBe false
        }
    }
})

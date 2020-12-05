package chapter15.solutions.ex7

import chapter15.sec2.Process
import chapter15.sec2.lift
import chapter15.sec2.toList
import chapter15.solutions.ex2.count
import chapter15.solutions.ex6.zip
import chapter3.List
import chapter5.Stream
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

//tag::init[]
fun <I, O> Process<I, O>.zipWithIndex(): Process<I, Pair<Int, O>> =
    zip(count<I>().map { it - 1 }, this)
//end::init[]

class Exercise7 : WordSpec({

    fun <I> pass(): Process<I, I> = lift { it }

    "zipWithIndex" should {
        "emit count of zero-based values along with stream each value" {
            val stream = Stream.of("a", "b", "c", "d", "e")
            pass<String>().zipWithIndex()(stream).toList() shouldBe
                List.of(
                    0 to "a",
                    1 to "b",
                    2 to "c",
                    3 to "d",
                    4 to "e"
                )
        }
    }
})

package chapter5.exercises.ex15

import chapter3.List
import chapter5.Stream
import chapter5.toList
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import utils.SOLUTION_HERE
import chapter3.Cons as ConsL
import chapter3.Nil as NilL

//TODO: Enable tests by removing `!` prefix
class Exercise15 : WordSpec({

    //tag::tails[]
    fun <A> Stream<A>.tails(): Stream<Stream<A>> =

        SOLUTION_HERE()
    //end::tails[]

    fun <A, B> List<A>.map(f: (A) -> B): List<B> =

        SOLUTION_HERE()

    "Stream.tails" should {
        "!return the stream of suffixes of the input sequence" {
            Stream.of(1, 2, 3)
                .tails()
                .toList()
                .map { it.toList() } shouldBe
                List.of(
                    ConsL(1, ConsL(2, ConsL(3, NilL))),
                    ConsL(2, ConsL(3, NilL)),
                    ConsL(3, NilL),
                    NilL
                )
        }
    }
})

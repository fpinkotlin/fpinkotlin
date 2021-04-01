package chapter5.exercises.ex16

import chapter3.List
import chapter5.Stream
import chapter5.toList
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import utils.SOLUTION_HERE

//TODO: Enable tests by removing `!` prefix
class Exercise16 : WordSpec({

    //tag::scanright[]
    fun <A, B> Stream<A>.scanRight(z: B, f: (A, () -> B) -> B): Stream<B> =

        SOLUTION_HERE()
    //end::scanright[]

    "Stream.scanRight" should {
        "!behave like foldRight" {
            Stream.of(1, 2, 3)
                .scanRight(0, { a, b ->
                    a + b()
                }).toList() shouldBe List.of(6, 5, 3, 0)
        }
    }
})

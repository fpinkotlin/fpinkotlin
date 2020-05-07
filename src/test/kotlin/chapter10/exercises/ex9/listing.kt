package chapter10.exercises.ex9

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

//tag::init1[]
fun ordered(ints: Sequence<Int>): Boolean = TODO()
//end::init1[]

class Exercise9 : WordSpec({
    "ordered using balanced fold" should {
        "!verify ordering ordered list" {
            ordered(sequenceOf(1, 2, 3, 4, 5, 6, 7, 8, 9)) shouldBe true
        }

        "!fail verification of unordered list" {
            ordered(sequenceOf(3, 2, 5, 6, 1, 4, 7, 9, 8)) shouldBe false
        }
    }
})

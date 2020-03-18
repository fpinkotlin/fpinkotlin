package chapter3.exercises

import chapter3.List
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

// tag::init[]
fun <A, B> foldLeftR(xs: List<A>, z: B, f: (B, A) -> B): B = TODO()

fun <A, B> foldRightL(xs: List<A>, z: B, f: (A, B) -> B): B = TODO()
// end::init[]

class Exercise_3_12 : WordSpec({
    "list foldLeftR" should {
        "!implement foldLeft functionality using foldRight" {
            foldLeftR(
                List.of(1, 2, 3, 4, 5),
                0,
                { x, y -> x + y }) shouldBe 15
        }
    }

    "list foldRightL" should {
        "!implement foldRight functionality using foldLeft" {
            foldRightL(
                List.of(1, 2, 3, 4, 5),
                0,
                { x, y -> x + y }) shouldBe 15
        }
    }
})

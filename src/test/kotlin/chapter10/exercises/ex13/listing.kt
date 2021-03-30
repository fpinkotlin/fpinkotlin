package chapter10.exercises.ex13

import chapter10.ForList
import chapter10.asConsList
import chapter10.solutions.ex12.Foldable
import chapter10.stringMonoid
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

//tag::init1[]
object ListFoldable : Foldable<ForList>
//end::init1[]

//TODO: Enable tests by removing `!` prefix
class Exercise13 : WordSpec({
    "ListFoldable" should {
        "!foldRight" {
            assertAll<List<Int>> { ls ->
                ListFoldable.foldRight(
                    ls.asConsList(),
                    0,
                    { a, b -> a + b }
                ) shouldBe ls.sum()
            }
        }
        "!foldLeft" {
            assertAll<List<Int>> { ls ->
                ListFoldable.foldLeft(
                    ls.asConsList(),
                    0,
                    { b, a -> a + b }) shouldBe ls.sum()
            }
        }
        "!foldMap" {
            assertAll<List<Int>> { ls ->
                ListFoldable.foldMap(
                    ls.asConsList(),
                    stringMonoid
                ) { it.toString() } shouldBe ls.joinToString("")
            }
        }
    }
})

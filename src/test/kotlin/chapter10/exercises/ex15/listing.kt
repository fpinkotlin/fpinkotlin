package chapter10.exercises.ex15

import chapter10.ForOption
import chapter10.None
import chapter10.Some
import chapter10.solutions.ex12.Foldable
import chapter10.stringMonoid
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

//tag::init1[]
object OptionFoldable : Foldable<ForOption>
//end::init1[]

//TODO: Enable tests by removing `!` prefix
class Exercise15 : WordSpec({
    "OptionFoldable" should {
        "!foldMap some" {
            OptionFoldable.foldMap(
                Some(1000),
                stringMonoid
            ) { it.toString() } shouldBe "1000"
        }
        "!foldMap none" {
            OptionFoldable.foldMap(
                None,
                stringMonoid
            ) { it.toString() } shouldBe ""
        }
        "!foldLeft some" {
            OptionFoldable.foldLeft(
                Some(1),
                "",
                { _, b -> b.toString() }) shouldBe "1"
        }
        "!foldLeft none" {
            OptionFoldable.foldLeft(
                None,
                "a",
                { _, b -> b.toString() }) shouldBe "a"
        }
        "!foldRight some" {
            OptionFoldable.foldRight(
                Some(1),
                "",
                { b, a -> b.toString() }) shouldBe "1"
        }
        "!foldRight none" {
            OptionFoldable.foldRight(
                None,
                "a",
                { b, a -> b.toString() }) shouldBe "a"
        }
    }
})

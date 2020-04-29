package chapter10.solutions.ex13

import chapter10.ForList
import chapter10.ListOf
import chapter10.asConsList
import chapter10.fix
import chapter10.solutions.ex12.Foldable
import chapter10.stringMonoid
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

//tag::init1[]
object ListFoldable : Foldable<ForList> {

    override fun <A, B> foldRight(
        fa: ListOf<A>,
        z: B,
        f: (A, B) -> B
    ): B =
        fa.fix().foldRight(z, f)

    override fun <A, B> foldLeft(
        fa: ListOf<A>,
        z: B,
        f: (B, A) -> B
    ): B =
        fa.fix().foldLeft(z, f)
}
//end::init1[]

class Exercise13 : WordSpec({
    "ListKFoldable" should {
        "foldRight" {
            assertAll<List<Int>> { ls ->
                ListFoldable.foldRight(
                    ls.asConsList(),
                    0,
                    { a, b -> a + b }
                ) shouldBe ls.sum()
            }
        }
        "foldLeft" {
            assertAll<List<Int>> { ls ->
                ListFoldable.foldLeft(
                    ls.asConsList(),
                    0,
                    { b, a -> a + b }) shouldBe ls.sum()
            }
        }
        "foldMap" {
            assertAll<List<Int>> { ls ->
                ListFoldable.foldMap(
                    ls.asConsList(),
                    stringMonoid
                ) { it.toString() } shouldBe ls.joinToString("")
            }
        }
    }
})

package chapter10.solutions.ex12

import arrow.Kind
import arrow.core.ForListK
import arrow.core.ForSequenceK
import arrow.core.ListK
import arrow.core.ListKOf
import arrow.core.SequenceKOf
import arrow.core.fix
import chapter10.Monoid
import chapter10.dual
import chapter10.endoMonoid
import chapter10.stringMonoid
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

//tag::init1[]
interface Foldable<F> {

    fun <A, B> foldRight(fa: Kind<F, A>, z: B, f: (A, B) -> B): B =
        foldMap(fa, endoMonoid()) { a: A -> { b: B -> f(a, b) } }(z)

    fun <A, B> foldLeft(fa: Kind<F, A>, z: B, f: (B, A) -> B): B =
        foldMap(fa, dual(endoMonoid())) { a: A -> { b: B -> f(b, a) } }(z)

    fun <A, B> foldMap(fa: Kind<F, A>, m: Monoid<B>, f: (A) -> B): B =
        foldRight(fa, m.zero, { a, b -> m.op(f(a), b) })
}

object ListKFoldable : Foldable<ForListK> {
    override fun <A, B> foldRight(
        fa: ListKOf<A>,
        z: B,
        f: (A, B) -> B
    ): B =
        fa.fix().foldRight(z, f)

    override fun <A, B> foldLeft(
        fa: ListKOf<A>,
        z: B,
        f: (B, A) -> B
    ): B =
        fa.fix().foldLeft(z, f)
}

object SequenceKFoldable : Foldable<ForSequenceK> {
    override fun <A, B> foldRight(
        fa: SequenceKOf<A>,
        z: B,
        f: (A, B) -> B
    ): B = TODO()
}
//end::init1[]

class Exercise12 : WordSpec({
    "ListKFoldable" should {
        "foldRight" {
            assertAll<List<Int>> { ls ->
                ListKFoldable.foldRight(
                    ListK(ls),
                    0,
                    { a, b -> a + b }) shouldBe ls.sum()
            }
        }
        "foldLeft" {
            assertAll<List<Int>> { ls ->
                ListKFoldable.foldLeft(
                    ListK(ls),
                    0,
                    { b, a -> a + b }) shouldBe ls.sum()
            }
        }
        "foldMap" {
            assertAll<List<Int>> { ls ->
                ListKFoldable.foldMap(
                    ListK(ls),
                    stringMonoid
                ) { it.toString() } shouldBe ls.joinToString("")
            }
        }
    }
})


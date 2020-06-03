package chapter10.solutions.ex14

import chapter10.Branch
import chapter10.ForTree
import chapter10.Leaf
import chapter10.Monoid
import chapter10.TreeOf
import chapter10.fix
import chapter10.intAdditionMonoid
import chapter10.intMultiplicationMonoid
import chapter10.solutions.ex12.Foldable
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

//tag::init1[]
object TreeFoldable : Foldable<ForTree> {
    override fun <A, B> foldMap(
        fa: TreeOf<A>,
        m: Monoid<B>,
        f: (A) -> B
    ): B =
        when (val t = fa.fix()) {
            is Leaf ->
                f(t.value)
            is Branch ->
                m.combine(foldMap(t.left, m, f), foldMap(t.right, m, f))
        }
}
//end::init1[]

class Exercise14 : WordSpec({
    "TreeFoldable" should {
        val t = Branch(Branch(Leaf(1), Leaf(2)), Branch(Leaf(3), Leaf(4)))
        "foldMap" {
            TreeFoldable.foldMap(t, intAdditionMonoid) { it } shouldBe 10
            TreeFoldable.foldMap(
                t,
                intMultiplicationMonoid
            ) { it } shouldBe 24
        }
        "foldRight" {
            TreeFoldable.foldRight(t, 0, { a, b -> a + b }) shouldBe 10
        }
        "foldLeft" {
            TreeFoldable.foldRight(t, 1, { a, b -> a * b }) shouldBe 24
        }
    }
})

package chapter3.exercises.ex28

import chapter3.Branch
import chapter3.Leaf
import chapter3.Tree
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import utils.SOLUTION_HERE

// tag::init[]
fun <A, B> fold(ta: Tree<A>, l: (A) -> B, b: (B, B) -> B): B =

    SOLUTION_HERE()

fun <A> sizeF(ta: Tree<A>): Int =

    SOLUTION_HERE()

fun maximumF(ta: Tree<Int>): Int =

    SOLUTION_HERE()

fun <A> depthF(ta: Tree<A>): Int =

    SOLUTION_HERE()

fun <A, B> mapF(ta: Tree<A>, f: (A) -> B): Tree<B> =

    SOLUTION_HERE()
// end::init[]

//TODO: Enable tests by removing `!` prefix
class Exercise28 : WordSpec({
    "tree fold" should {

        val tree = Branch(
            Branch(Leaf(1), Leaf(2)),
            Branch(
                Leaf(3),
                Branch(
                    Branch(Leaf(4), Leaf(5)),
                    Branch(
                        Leaf(21),
                        Branch(Leaf(7), Leaf(8))
                    )
                )
            )
        )
        "!generalise size" {
            sizeF(tree) shouldBe 15
        }

        "!generalise maximum" {
            maximumF(tree) shouldBe 21
        }

        "!generalise depth" {
            depthF(tree) shouldBe 5
        }

        "!generalise map" {
            mapF(tree) { it * 10 } shouldBe
                Branch(
                    Branch(Leaf(10), Leaf(20)),
                    Branch(
                        Leaf(30),
                        Branch(
                            Branch(Leaf(40), Leaf(50)),
                            Branch(
                                Leaf(210),
                                Branch(Leaf(70), Leaf(80))
                            )
                        )
                    )
                )
        }
    }
})

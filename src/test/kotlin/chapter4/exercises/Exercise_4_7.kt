package chapter4.exercises

import chapter3.List
import chapter4.Either
import chapter4.Left
import chapter4.Right
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class Exercise_4_7 : WordSpec({

    //tag::init[]
    fun <E, A, B> traverse(
        xs: List<A>,
        f: (A) -> Either<E, B>
    ): Either<E, List<B>> = TODO()

    fun <E, A> sequence(es: List<Either<E, A>>): Either<E, List<A>> =
        TODO()
    //end::init[]

    fun <A> catches(a: () -> A): Either<String, A> = TODO()

    "traverse" should {
        """!return a right either of a transformed list if all
            transformations succeed""" {
                val xa = List.of("1", "2", "3", "4", "5")

                traverse(xa) { a ->
                    catches { Integer.parseInt(a) }
                } shouldBe Right(List.of(1, 2, 3, 4, 5))
            }

        "!return a left either if any transformations fail" {
            val xa = List.of("1", "2", "x", "4", "5")

            traverse(xa) { a ->
                catches { Integer.parseInt(a) }
            } shouldBe Left(
                """For input string: "x""""
            )
        }
    }
    "sequence" should {
        "!turn a list of right eithers into a right either of list" {
            val xe: List<Either<String, Int>> =
                List.of(Right(1), Right(2), Right(3))

            sequence(xe) shouldBe Right(List.of(1, 2, 3))
        }

        """!convert a list containing any left eithers into a
            left either""" {
                val xe: List<Either<String, Int>> =
                    List.of(Right(1), Left("boom"), Right(3))

                sequence(xe) shouldBe Left("boom")
            }
    }
})

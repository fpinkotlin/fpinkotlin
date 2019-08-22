package chapter4

import chapter3.listings.Cons
import chapter3.listings.List
import chapter3.listings.Nil
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

sealed class Either<out E, out A>

data class Left<out E>(val value: E) : Either<E, Nothing>()
data class Right<out A>(val value: A) : Either<Nothing, A>()

class SolutionSpec_4_7 : WordSpec({

    fun <E, A, B> traverse(xs: List<A>, f: (A) -> Either<E, B>): Either<E, List<B>> =
            when (xs) {
                is Nil -> Right(Nil)
                is Cons ->
                    map2(f(xs.head), traverse(xs.tail, f)) { b, xb -> Cons(b, xb) }
            }

    fun <E, A> sequence(es: List<Either<E, A>>): Either<E, List<A>> = traverse(es) { it }

    fun <A> Try(a: () -> A): Either<String, A> =
            try {
                Right(a())
            } catch (e: Throwable) {
                Left(e.message!!)
            }

    "traverse" should {
        "return a right either of a transformed list if all transformations succeed" {
            val xa = List.of("1", "2", "3", "4", "5")

            traverse(xa) { a -> Try { Integer.parseInt(a) } } shouldBe Right(List.of(1, 2, 3, 4, 5))
        }

        "return a left either if any transformations fail" {
            val xa = List.of("1", "2", "x", "4", "5")

            traverse(xa) { a -> Try { Integer.parseInt(a) } } shouldBe Left("""For input string: "x"""")
        }
    }
    "sequence" should {
        "turn a list of right eithers into a right either of list" {
            val xe: List<Either<String, Int>> = List.of(Right(1), Right(2), Right(3))

            sequence(xe) shouldBe Right(List.of(1, 2, 3))
        }

        "convert a list containing any left eithers into a left either" {
            val xe: List<Either<String, Int>> = List.of(Right(1), Left("boom"), Right(3))

            sequence(xe) shouldBe Left("boom")
        }
    }
})
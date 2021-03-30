package chapter4.exercises.ex6

import chapter4.Either
import chapter4.Left
import chapter4.Right
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import utils.SOLUTION_HERE

//tag::init[]
fun <E, A, B> Either<E, A>.map(f: (A) -> B): Either<E, B> =

    SOLUTION_HERE()

fun <E, A, B> Either<E, A>.flatMap(f: (A) -> Either<E, B>): Either<E, B> =

    SOLUTION_HERE()

fun <E, A> Either<E, A>.orElse(f: () -> Either<E, A>): Either<E, A> =

    SOLUTION_HERE()

fun <E, A, B, C> map2(
    ae: Either<E, A>,
    be: Either<E, B>,
    f: (A, B) -> C
): Either<E, C> =

    SOLUTION_HERE()
//end::init[]

//TODO: Enable tests by removing `!` prefix
class Exercise6 : WordSpec({

    val right: Either<Throwable, Int> = Right(1)

    val left: Either<Throwable, Int> = Left(Throwable("boom"))

    "either map" should {
        "!transform a right value" {
            right.map { it.toString() } shouldBe Right("1")
        }
        "!pass over a left value" {
            left.map { it.toString() } shouldBe left
        }
    }

    "either orElse" should {
        "!return the either if it is right" {
            right.orElse { left } shouldBe right
        }
        "!pass the default value if either is left" {
            left.orElse { right } shouldBe right
        }
    }

    "either flatMap" should {
        "!apply a function yielding an either to a right either" {
            right.flatMap { a ->
                Right(a.toString())
            } shouldBe Right("1")
        }
        "!pass on the left value" {
            left.flatMap { a ->
                Right(a.toString())
            } shouldBe left
        }
    }

    "either map2" should {
        val right1: Right<Int> = Right(3)
        val right2: Right<Int> = Right(2)
        val left1: Either<Throwable, Int> =
            Left(IllegalArgumentException("boom"))
        val left2: Either<Throwable, Int> =
            Left(IllegalStateException("pow"))

        "!combine two either right values using a binary function" {
            map2(
                right1,
                right2
            ) { a, b -> (a * b).toString() } shouldBe Right("6")
        }
        "!return left if either is left" {
            map2(
                right1,
                left1
            ) { a, b -> (a * b).toString() } shouldBe left1
        }
        "!return the first left if both are left" {
            map2(
                left1,
                left2
            ) { a, b -> (a * b).toString() } shouldBe left1
        }
    }
})

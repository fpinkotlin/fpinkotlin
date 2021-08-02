package chapter15.exercises.ex1

// import chapter10.None
// import chapter10.Option
// import chapter10.Some
// import chapter15.sec2.Await
// import chapter15.sec2.Emit
// import chapter15.sec2.Halt
import chapter15.sec2.Process
import chapter15.sec2.toList
import chapter3.List
import chapter5.Stream
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import utils.SOLUTION_HERE

//tag::init[]
fun <I> take(n: Int): Process<I, I> =

    SOLUTION_HERE()

fun <I> drop(n: Int): Process<I, I> =

    SOLUTION_HERE()

fun <I> takeWhile(p: (I) -> Boolean): Process<I, I> =

    SOLUTION_HERE()

fun <I> dropWhile(p: (I) -> Boolean): Process<I, I> =

    SOLUTION_HERE()
//end::init[]

//TODO: Enable tests by removing `!` prefix
class Exercise1 : WordSpec({
    val stream = Stream.of(1, 2, 3, 4, 5, 6, 7, 6, 5, 4, 3, 2, 1)
    "take" should {
        "!consume the given number of elements from a stream" {
            take<Int>(5)(stream).toList() shouldBe List.of(1, 2, 3, 4, 5)
        }
    }
    "drop" should {
        "!drop the given number of elements from a stream" {
            drop<Int>(5)(stream).toList() shouldBe
                List.of(6, 7, 6, 5, 4, 3, 2, 1)
        }
    }
    "takeWhile" should {
        "!consume elements from a stream while a predicate is true" {
            takeWhile<Int> { 5 > it }(stream).toList() shouldBe
                List.of(1, 2, 3, 4)
        }
    }
    "dropWhile" should {
        "!drop elements from a stream while a predicate is true" {
            dropWhile<Int> { 5 > it }(stream).toList() shouldBe
                List.of(5, 6, 7, 6, 5, 4, 3, 2, 1)
        }
    }
})

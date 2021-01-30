package chapter15.exercises.ex6

// import chapter10.Option
// import chapter15.sec2.Await
// import chapter15.sec2.Emit
// import chapter15.sec2.Halt
import chapter15.sec2.Process
import chapter15.sec2.sum
import chapter15.sec2.toList
import chapter15.solutions.ex2.count
import chapter3.List
import chapter5.Stream
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
fun <A, B, C> zip(
    p1: Process<A, B>,
    p2: Process<A, C>
): Process<A, Pair<B, C>> = TODO()

//tag::init[]
fun mean(): Process<Double, Double> = TODO()
//end::init[]

class Exercise6 : WordSpec({

    val stream = Stream.of(1.0, 2.0, 3.0, 4.0, 5.0, 6.0)

    "the combinator" should {
        "!feed input into two processes" {
            val p1 = sum()
            val p2 = count<Double>()
            zip(p1, p2)(stream).toList() shouldBe List.of(
                1.0 to 1,
                3.0 to 2,
                6.0 to 3,
                10.0 to 4,
                15.0 to 5,
                21.0 to 6
            )
        }
    }

    "mean" should {
        "!calculate a running average of values encounterd so far" {
            val p = mean()
            p(stream).toList() shouldBe
                List.of(1.0, 1.5, 2.0, 2.5, 3.0, 3.5)
        }
    }
})

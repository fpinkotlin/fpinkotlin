package chapter15.exercises.ex5

// import chapter10.None
// import chapter10.Some
// import chapter15.sec2.Await
// import chapter15.sec2.Emit
// import chapter15.sec2.Halt
import chapter15.sec2.Process
import chapter15.sec2.sum
import chapter15.sec2.toList
import chapter15.solutions.ex1.take
import chapter3.List
import chapter5.Stream
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

//tag::init[]
infix fun <I, O, O2> Process<I, O>.pipe(
    g: Process<O, O2>
): Process<I, O2> = TODO()
//end::init[]

class Exercise5 : WordSpec({
    "pipe" should {
        "!fuse together two processes" {
            val stream = Stream.of(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
            val sumP: Process<Double, Double> = sum()
            val take4 = take<Double>(4)
            val fused = sumP pipe take4
            fused(stream).toList() shouldBe List.of(1.0, 3.0, 6.0, 10.0)
        }
    }
})

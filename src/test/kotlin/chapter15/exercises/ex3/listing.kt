package chapter15.exercises.ex3

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

//tag::init[]
fun mean(): Process<Double, Double> = TODO()
//end::init[]

class Exercise3 : WordSpec({
    "mean" should {
        "!calculate a running average of values encounterd so far" {
            val stream = Stream.of(1.0, 2.0, 3.0, 4.0, 5.0)
            val p = mean()
            p(stream).toList() shouldBe List.of(1.0, 1.5, 2.0, 2.5, 3.0)
        }
    }
})
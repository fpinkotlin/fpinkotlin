package chapter15.solutions.ex5

import chapter10.None
import chapter10.Some
import chapter15.product
import chapter15.sec2.Await
import chapter15.sec2.Emit
import chapter15.sec2.Halt
import chapter15.sec2.Process
import chapter15.sec2.lift
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
): Process<I, O2> =
    when (g) {
        is Halt -> Halt()
        is Emit -> Emit(g.head, this pipe g.tail)
        is Await -> when (this) {
            is Emit -> this.tail pipe g.recv(Some(this.head))
            is Halt -> Halt<I, O>() pipe g.recv(None)
            is Await -> Await { i -> this.recv(i) pipe g }
        }
    }
//end::init[]

class Exercise5 : WordSpec({
    "pipe" should {
        "fuse together two processes" {
            val stream = Stream.of(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
            val sumP: Process<Double, Double> = sum()
            val take4 = take<Double>(4)
            val fused = sumP pipe take4
            fused(stream).toList() shouldBe List.of(1.0, 3.0, 6.0, 10.0)
        }
        "fuse together two processes (test 2 rectus)" {
            val stream = Stream.of(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
            val incP: Process<Double, Double> = lift { n: Double -> n + 1 }
            val productP: Process<Double, Double> = product()
            val fused = incP pipe productP
            // incP: (2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
            // productP: (2.0, 6.0, 24.0, 120.0, 720.0, 5040.0, 40320.0)
            fused(stream).toList() shouldBe
                List.of(2.0, 6.0, 24.0, 120.0, 720.0, 5040.0, 40320.0)
        }
        "fuse together two processes (test 2 inversus)" {
            val stream = Stream.of(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
            val productP: Process<Double, Double> = product()
            val incP: Process<Double, Double> = lift { n: Double -> n + 1 }
            val fused = productP pipe incP
            // productP: (1.0, 2.0, 3.0, 24.0, 120.0, 720.0, 5040.0)
            // incP: (2.0, 3.0, 7.0, 25.0, 121.0, 721.0, 5041.0)
            fused(stream).toList() shouldBe
                List.of(2.0, 3.0, 7.0, 25.0, 121.0, 721.0, 5041.0)
        }
    }
})

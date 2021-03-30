package chapter15.solutions.ex4

import chapter10.None
import chapter10.Option
import chapter10.Some
import chapter15.sec2.Await
import chapter15.sec2.Emit
import chapter15.sec2.Halt
import chapter15.sec2.Process
import chapter15.sec2.toList
import chapter3.List
import chapter5.Stream
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

fun <S, I, O> loop(z: S, f: (I, S) -> Pair<O, S>): Process<I, O> =
    Await { i: Option<I> ->
        when (i) {
            is Some -> {
                val (o, s2) = f(i.get, z)
                Emit(o, loop(s2, f))
            }
            is None -> Halt<I, O>()
        }
    }

//tag::init[]
fun sum(start: Double): Process<Double, Double> =
    loop(0.0) { i: Double, acc: Double -> (acc + i) to (acc + i) }

fun <I> count(): Process<I, Int> =
    loop(0) { _, n: Int -> (n + 1) to (n + 1) }
//end::init[]

class Exercise4 : WordSpec({
    val stream = Stream.of(1.0, 2.0, 3.0, 4.0, 5.0)
    "sum" should {
        "produce a stream that accumulates a sum of values of a stream" {
            sum(0.0)(stream).toList() shouldBe
                List.of(1.0, 3.0, 6.0, 10.0, 15.0)
        }
    }
    "count" should {
        "produce a stream counting the number of elements in a stream" {
            count<Double>()(stream).toList() shouldBe
                List.of(1, 2, 3, 4, 5)
        }
    }
})

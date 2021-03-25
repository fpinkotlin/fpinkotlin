package chapter15.solutions.ex8

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

//tag::init1[]
fun <I> exists(f: (I) -> Boolean): Process<I, Boolean> =
    Await { i: Option<I> ->
        when (i) {
            is Some ->
                Emit<I, Boolean>(
                    f(i.get),
                    exists { f(i.get) || f(it) }
                )
            is None -> Halt<I, Boolean>()
        }
    }
//end::init1[]

//tag::init2[]
fun <I> existsAndHalts(f: (I) -> Boolean): Process<I, Boolean> =
    Await { i: Option<I> ->
        when (i) {
            is Some ->
                if (f(i.get)) {
                    Emit<I, Boolean>(true)
                } else {
                    Emit<I, Boolean>(
                        false,
                        existsAndHalts { f(it) }
                    )
                }
            is None -> Halt<I, Boolean>()
        }
    }
//end::init2[]

class Exercise8 : WordSpec({
    "exists" should {
        "not halt and yield all intermediate results" {
            val stream = Stream.of(1, 3, 5, 6, 7)
            val p = exists<Int> { i -> i % 2 == 0 }
            p(stream).toList() shouldBe
                List.of(false, false, false, true, true)
        }
        "halt and yield all intermediate results" {
            val stream = Stream.of(1, 3, 5, 6, 7)
            val p = existsAndHalts<Int> { i -> i % 2 == 0 }
            p(stream).toList() shouldBe
                    List.of(false, false, false, true)
        }
    }
})

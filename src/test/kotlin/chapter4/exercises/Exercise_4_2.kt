package chapter4.exercises

import chapter3.List
import chapter4.Boilerplate.isEmpty
import chapter4.Boilerplate.size
import chapter4.Boilerplate.sum
import chapter4.None
import chapter4.Option
import chapter4.Some
import io.kotlintest.matchers.doubles.plusOrMinus
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

fun mean(xs: List<Double>): Option<Double> =
    if (xs.isEmpty()) None
    else Some(xs.sum() / xs.size())

//tag::init[]
fun variance(xs: List<Double>): Option<Double> = TODO()
//end::init[]

class Exercise_4_2 : WordSpec({

    "variance" should {
        "!determine the variance of a list of numbers" {
            val ls =
                List.of(1.0, 1.1, 1.0, 3.0, 0.9, 0.4)
            variance(ls).getOrElse { 0.0 } shouldBe
                (0.675).plusOrMinus(0.005)
        }
    }
})

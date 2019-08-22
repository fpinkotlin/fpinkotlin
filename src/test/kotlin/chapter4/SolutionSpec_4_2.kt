package chapter4

import chapter3.listings.List
import io.kotlintest.matchers.doubles.plusOrMinus
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import kotlin.math.pow

//using `mean` method from listing 4.2
fun mean(xs: List<Double>): Option<Double> =
        if (xs.isEmpty()) None
        else Some(xs.sum() / xs.size())

fun variance(xs: List<Double>): Option<Double> =
        mean(xs).flatMap { m -> mean(xs.map { x -> (x - m).pow(2) }) }

class SolutionSpec_4_2 : WordSpec({

    "variance" should {
        "determine the variance of a list of numbers" {
            val ls = List.of(1.0, 1.1, 1.0, 3.0, 0.9, 0.4)
            variance(ls).getOrElse { 0.0 } shouldBe (0.675).plusOrMinus(0.005)
        }
    }
})
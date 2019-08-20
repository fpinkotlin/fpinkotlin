package chapter4

import chapter4.Listing_4_2.mean3
import io.kotlintest.matchers.doubles.plusOrMinus
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import kotlin.math.pow

fun variance(xs: List<Double>): Option<Double> =
        mean3(xs).flatMap { m -> mean3(xs.map { x -> (x - m).pow(2) }) }

class SolutionSpec_4_2 : WordSpec({
    //using mean3 method that returns an Option<Double>
    "variance" should {
        "determine the variance of a list of numbers" {
            val ls = listOf(1.0, 1.1, 1.0, 3.0, 0.9, 0.4)
            variance(ls).getOrElse { 0.0 } shouldBe(0.675).plusOrMinus(0.005)
        }
    }
})
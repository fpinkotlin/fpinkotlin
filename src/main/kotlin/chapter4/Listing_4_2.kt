package chapter4

object Listing_4_2 {
    fun mean(xs: List<Double>): Double =
            if (xs.isEmpty())
                throw ArithmeticException("mean of emtpy list!")
            else xs.sum() / xs.size

    fun mean2(xs: List<Double>, onEmpty: Double) =
            if (xs.isEmpty()) onEmpty
            else xs.sum() / xs.size

    fun mean3(xs: List<Double>): Option<Double> =
            if (xs.isEmpty()) None
            else Some(xs.sum() / xs.size)
}

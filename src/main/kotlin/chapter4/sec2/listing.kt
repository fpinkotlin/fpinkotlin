package chapter4.sec2

sealed class List<out A>

private fun <A> length(xs: List<A>): Int = TODO()

private fun List<Double>.sum(): Double = TODO()

private fun List<Double>.isEmpty(): Boolean = TODO()

fun <A> List<A>.size(): Int = TODO()

val listing1 = {
    //tag::init1[]
    fun mean(xs: List<Double>): Double =
        if (xs.isEmpty())
            throw ArithmeticException("mean of emtpy list!") // <1>
        else xs.sum() / length(xs) // <2>
    //end::init1[]
}

val listing2 = {
    //tag::init2[]
    fun mean(xs: List<Double>, onEmpty: Double) =
        if (xs.isEmpty()) onEmpty // <1>
        else xs.sum() / xs.size() // <2>
    //end::init2[]
}

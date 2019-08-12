package chapter3

//tag::of[]
fun <A> of(vararg aa: A): List<A> {
    val tail = aa.sliceArray(1 until aa.size)
    return if (aa.isEmpty()) Nil else Cons(aa[0], List.of(*tail))
}
//end::of[]

//tag::sum[]
fun sum(xs: List<Int>): Int =
        when (xs) {
            is Nil -> 0
            is Cons -> xs.head + sum(xs.tail)
        }
//end::sum[]

//tag::product[]
fun product(xs: List<Double>): Double =
        when (xs) {
            is Nil -> 1.0
            is Cons ->
                if (xs.head == 0.0) 0.0
                else xs.head * product(xs.tail)
        }
//end::product[]

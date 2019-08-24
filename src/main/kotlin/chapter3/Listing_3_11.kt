package chapter3

object Listing_3_11 {

    //tag::init[]
    fun <A, B> foldRight(xs: List<A>, z: B, f: (A, B) -> B): B =
            when (xs) {
                is Nil -> z
                is Cons -> f(xs.head, foldRight(xs.tail, z, f))
            }

    fun sum2(ints: List<Int>): Int = foldRight(ints, 0, { a, b -> a + b })

    fun product2(dbs: List<Double>): Double = foldRight(dbs, 1.0, { a, b -> a * b })
    //end::init[]
}
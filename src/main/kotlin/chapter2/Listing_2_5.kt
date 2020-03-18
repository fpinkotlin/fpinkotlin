package chapter2

object Listing_2_5 {
    //tag::init[]
    fun <A> findFirst(xs: Array<A>, p: (A) -> Boolean): Int { // <1>
        tailrec fun loop(n: Int): Int =
            when {
                n >= xs.size -> -1
                p(xs[n]) -> n // <2>
                else -> loop(n + 1)
            }
        return loop(0)
    }
    //end::init[]
}

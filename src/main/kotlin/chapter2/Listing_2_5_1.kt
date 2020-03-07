package chapter2

object Listing_2_5_1 {
    //tag::init[]
    fun <A> findFirst(xs: Array<A>, p: (A) -> Boolean): Int {
        tailrec fun loop(n: Int): Int =
            when { // <1>
                n >= xs.size -> -1 // <2>
                p(xs[n]) -> n
                else -> loop(n + 1) // <3>
            }
        return loop(0)
    }
    //end::init[]
}

package chapter2

//Listing 2.5
object Listing_2_5 {
    fun <A> findFirst(xs: Array<A>, p: (A) -> Boolean): Int {
        tailrec fun loop(n: Int): Int =
                if (n >= xs.size) -1
                else if (p(xs[n])) n
                else loop(n + 1)
        return loop(0)
    }
}
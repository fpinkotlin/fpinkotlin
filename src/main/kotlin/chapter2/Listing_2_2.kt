package chapter2

//Listing 2.2
object Listing_2_2 {

    fun factorial(i: Int): Int {
        fun go(n: Int, acc: Int): Int =
                if (n <= 0) acc
                else go(n - 1, n * acc)
        return go(i, 1)
    }
}

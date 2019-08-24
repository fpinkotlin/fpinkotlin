package chapter2

//Listing 2.3 (after refactoring)
object Listing_2_3 {

    fun abs(n: Int): Int =
            if (n < 0) -n
            else n

    fun factorial(i: Int): Int {
        fun go(n: Int, acc: Int): Int =
                if (n <= 0) acc
                else go(n - 1, n * acc)
        return go(i, 1)
    }

    fun formatResult(name: String, n: Int, f: (Int) -> Int): String {
        val msg = "The %s of %d is %d."
        return msg.format(name, n, f(n))
    }
}

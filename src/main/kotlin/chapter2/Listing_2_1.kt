package chapter2

//Listing 2.1
object Listing_2_1 {

    private fun abs(n: Int): Int =
            if (n < 0) -n
            else n

    fun formatAbs(x: Int): String {
        val msg = "The absolute value of %d is %d"
        return msg.format(x, abs(x))
    }
}

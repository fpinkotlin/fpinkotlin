package chapter2

//Listing 2.4
object Listing_2_4 {
    fun findFirst(ss: Array<String>, key: String): Int {
        tailrec fun loop(n: Int): Int =
                if (n >= ss.size) -1
                else if (ss[n] == key) n
                else loop(n + 1)
        return loop(0)
    }
}

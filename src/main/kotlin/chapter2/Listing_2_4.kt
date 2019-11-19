package chapter2

object Listing_2_4 {

    //tag::init[]
    fun findFirst(ss: Array<String>, key: String): Int {
        tailrec fun loop(n: Int): Int =
            when {
                n >= ss.size -> -1 // <1>
                ss[n] == key -> n // <2>
                else -> loop(n + 1) // <3>
            }
        return loop(0) // <4>
    }
    //end::init[]
}

package chapter2

object Listing_2_2 {

    //tag::factorial[]
    fun factorial(i: Int): Int {
        fun go(n: Int, acc: Int): Int = // <1>
            if (n <= 0) acc
            else go(n - 1, n * acc)
        return go(i, 1) // <2>
    }
    //end::factorial[]
}

object Listing_2_2_1 {

    //tag::factorial2[]
    fun factorial(i: Int): Int {
        tailrec fun go(n: Int, acc: Int): Int = // <1>
            if (n <= 0) acc
            else go(n - 1, n * acc) // <2>
        return go(i, 1)
    }
    //end::factorial2[]
}

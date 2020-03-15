package chapter2

object Listing_2_1_1 {
    //tag::abs[]
    private fun abs(n: Int): Int =
        if (n < 0) -n else n
    //end::abs[]

    //tag::formatabs[]
    fun formatAbs(x: Int): String {
        val msg = "The absolute value of %d is %d"
        return msg.format(x, abs(x))
    }
    //end::formatabs[]

    //tag::main[]
    fun main(args: Array<String>) {
        println(formatAbs(-42))
    }
    //end::main[]

    //tag::main2[]
    fun main() = println(formatAbs(-42))
    //end::main2[]
}

object Listing_2_1_2 {
    //tag::abs2[]
    infix fun abs(n: Int): Int = if (n < 0) -n else n
    //end::abs2[]
}

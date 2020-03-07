package chapter2

object Listing_2_3_1 {
    fun factorial(i: Int): Int = TODO()

    fun abs(n: Int): Int = TODO()

    //tag::formatresult[]
    fun formatResult(name: String, n: Int, f: (Int) -> Int): String {
        val msg = "The %s of %d is %d."
        return msg.format(name, n, f(n))
    }
    //end::formatresult[]

    //tag::main[]
    fun main() {
        println(formatResult("factorial", 7, ::factorial))
        println(formatResult("absolute value", -42, ::abs))
    }
    //end::main[]
}

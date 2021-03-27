package chapter2.sec1

val listing1 = {
    //tag::init1[]
    fun factorial(i: Int): Int {
        fun go(n: Int, acc: Int): Int = // <1>
            if (n <= 0) acc
            else go(n - 1, n * acc)
        return go(i, 1) // <2>
    }
    //end::init1[]
}

val listing2 = {
    //tag::init2[]
    fun factorial(i: Int): Int {
        tailrec fun go(n: Int, acc: Int): Int = // <1>
            if (n <= 0) acc
            else go(n - 1, n * acc) // <2>
        return go(i, 1)
    }
    //end::init2[]
}

//tag::init3[]
object Example {

    private fun abs(n: Int): Int =
        if (n < 0) -n
        else n

    private fun factorial(i: Int): Int { //<1>
        fun go(n: Int, acc: Int): Int =
            if (n <= 0) acc
            else go(n - 1, n * acc)
        return go(i, 1)
    }

    fun formatAbs(x: Int): String {
        val msg = "The absolute value of %d is %d"
        return msg.format(x, abs(x))
    }

    fun formatFactorial(x: Int): String { //<2>
        val msg = "The factorial of %d is %d"
        return msg.format(x, factorial(x))
    }
}

fun main() {
    println(Example.formatAbs(-42))
    println(Example.formatFactorial(7)) //<3>
}
//end::init3[]

val listing4 = {
    fun factorial(i: Int): Int = TODO()

    fun abs(n: Int): Int = TODO()

    //tag::init4[]
    fun formatResult(name: String, n: Int, f: (Int) -> Int): String {
        val msg = "The %s of %d is %d."
        return msg.format(name, n, f(n))
    }
    //end::init4[]

    //tag::init5[]
    fun main() {
        println(formatResult("factorial", 7, ::factorial))
        println(formatResult("absolute value", -42, ::abs))
    }
    //end::init5[]
}

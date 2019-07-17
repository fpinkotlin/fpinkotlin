package chapter2

import chapter2.MyModule23.abs
import chapter2.MyModule23.factorial

//Listing 2.1
object MyModule21 {

    private fun abs(n: Int): Int =
            if (n < 0) -n
            else n

    fun formatAbs(x: Int): String {
        val msg = "The absolute value of %d is %d"
        return msg.format(x, abs(x))
    }
}

//Listing 2.2
fun factorial22(i: Int): Int {
    fun go(n: Int, acc: Int): Int =
            if (n <= 0) acc
            else go(n - 1, n * acc)
    return go(i, 1)
}

//Listing 2.3 (after refactoring)
object MyModule23 {

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

fun main() {
    println(MyModule23.formatResult("abs", 42, ::abs))
    println(MyModule23.formatResult("factorial", 7, ::factorial))
}

//Listing 2.4
fun findFirst(ss: Array<String>, key: String): Int {
    tailrec fun loop(n: Int): Int =
            if (n >= ss.size) -1
            else if (ss[n] == key) n
            else loop(n + 1)
    return loop(0)
}

//Listing 2.5
fun <A> findFirst(xs: Array<A>, p: (A) -> Boolean): Int {
    tailrec fun loop(n: Int): Int =
            if (n >= xs.size) -1
            else if (p(xs[n])) n
            else loop(n + 1)
    return loop(0)
}

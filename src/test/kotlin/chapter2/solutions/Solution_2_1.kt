package chapter2.solutions

object Solution_2_1 {
    // tag::exercise2.1[]
    fun fib(i: Int): Int {
        tailrec fun go(cnt: Int, curr: Int, nxt: Int): Int =
                if (cnt == 0)
                    curr
                else go(cnt - 1, nxt, curr + nxt)
        return go(i, 0, 1)
    }
    // end::exercise2.1[]
}
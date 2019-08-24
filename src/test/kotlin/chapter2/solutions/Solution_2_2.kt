package chapter2.solutions

object Solution_2_2 {
    // tag::exercise2.2[]
    val <T> List<T>.tail: List<T>
        get() = drop(1)

    val <T> List<T>.head: T
        get() = first()

    fun <A> isSorted(aa: List<A>, ordered: (A, A) -> Boolean): Boolean {
        tailrec fun go(x: A, xs: List<A>): Boolean =
                if (ordered(x, xs.head))
                    if (xs.tail.isNotEmpty()) {
                        go(xs.head, xs.tail)
                    } else true
                else false

        return go(aa.head, aa.tail)
    }
    // end::exercise2.2[]
}
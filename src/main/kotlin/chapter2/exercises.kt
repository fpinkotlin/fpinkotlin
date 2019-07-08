package chapter2

///Ex 3.1
fun fib(i: Int): Int {
    tailrec fun go(cnt: Int, curr: Int, nxt: Int): Int =
            if (cnt == 0)
                curr
            else go(cnt - 1, nxt, curr + nxt)
    return go(i, 0, 1)
}

//Ex 3.2
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

//Ex 3.3
fun <A, B, C> curry(f: (A, B) -> C): (A) -> (B) -> C = { a: A -> { b: B -> f(a, b) } }

//Ex 3.4
fun <A, B, C> uncurry(f: (A) -> (B) -> C): (A, B) -> C = { a: A, b: B -> f(a)(b) }

//Ex 3.5
fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C = { a: A -> f(g(a)) }

package chapter12.solutions.ex15

import chapter10.Monoid

//tag::init[]
data class Iterator<A>(val a: A, val f: (A) -> A, val n: Int) {
    fun <B> foldMap(g: (A) -> B, m: Monoid<B>): B {
        tailrec fun iterate(n: Int, b: B, c: A): B =
            if (n <= 0) b else iterate(n - 1, g(c), f(a))
        return iterate(n, m.nil, a)
    }
}
//end::init[]

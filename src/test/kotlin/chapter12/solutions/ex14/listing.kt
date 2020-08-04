package chapter12.solutions.ex14

import chapter10.Monoid

//tag::init[]
data class Iterator<A>(val a: A, val f: (A) -> A, val n: Int) {
    fun <B> foldMap(fn: (A) -> B, m: Monoid<B>): B {
        tailrec fun iterate(len: Int, nil: B, aa: A): B =
            if (len <= 0) nil else iterate(len - 1, fn(aa), f(a))
        return iterate(n, m.nil, a)
    }
}
//end::init[]

package chapter2.sec2

//tag::init1[]
fun findFirst(ss: Array<String>, key: String): Int {
    tailrec fun loop(n: Int): Int =
        when {
            n >= ss.size -> -1 // <1>
            ss[n] == key -> n // <2>
            else -> loop(n + 1) // <3>
        }
    return loop(0) // <4>
}
//end::init1[]

//tag::init2[]
fun <A> findFirst(xs: Array<A>, p: (A) -> Boolean): Int { // <1>
    tailrec fun loop(n: Int): Int =
        when {
            n >= xs.size -> -1
            p(xs[n]) -> n // <2>
            else -> loop(n + 1)
        }
    return loop(0)
}
//end::init2[]

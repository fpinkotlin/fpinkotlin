package chapter2.sec3

val listing1 = {
    //tag::init1[]
    fun <A, B, C> partial1(a: A, f: (A, B) -> C): (B) -> C = TODO()
    //end::init1[]
}

val listing2 = {
    //tag::init2[]
    fun <A, B, C> partial1(a: A, f: (A, B) -> C): (B) -> C =
        { b: B -> TODO() }
    //end::init2[]
}

val listing3 = {
    //tag::init3[]
    fun <A, B, C> partial1(a: A, f: (A, B) -> C): (B) -> C =
        { b: B -> f(a, b) }
    //end::init3[]
}

val listing4 = {
    //tag::init4[]
    fun <A, B, C> partial1(a: A, f: (A, B) -> C): (B) -> C =
        { b -> f(a, b) }
    //end::init4[]
}

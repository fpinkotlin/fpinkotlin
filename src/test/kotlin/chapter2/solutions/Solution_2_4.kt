package chapter2.solutions

object Solution_2_4 {
    // tag::init[]
    fun <A, B, C> uncurry(f: (A) -> (B) -> C): (A, B) -> C =
            { a: A, b: B -> f(a)(b) }
    // end::init[]
}
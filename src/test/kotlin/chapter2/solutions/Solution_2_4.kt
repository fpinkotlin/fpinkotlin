package chapter2.solutions

object Solution_2_4 {
    // tag::exercise2.4[]
    fun <A, B, C> uncurry(f: (A) -> (B) -> C): (A, B) -> C = { a: A, b: B -> f(a)(b) }
    // end::exercise2.4[]
}
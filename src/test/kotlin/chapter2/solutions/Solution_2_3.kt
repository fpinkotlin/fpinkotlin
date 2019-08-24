package chapter2.solutions

object Solution_2_3 {
    // tag::exercise2.3[]
    fun <A, B, C> curry(f: (A, B) -> C): (A) -> (B) -> C = { a: A -> { b: B -> f(a, b) } }
    // end::exercise2.3[]
}
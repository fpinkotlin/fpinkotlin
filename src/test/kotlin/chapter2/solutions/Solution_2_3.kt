package chapter2.solutions

object Solution_2_3 {
    // tag::init[]
    fun <A, B, C> curry(f: (A, B) -> C): (A) -> (B) -> C =
            { a: A -> { b: B -> f(a, b) } }
    // end::init[]
}
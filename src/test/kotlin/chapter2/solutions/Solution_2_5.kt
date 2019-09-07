package chapter2.solutions

object Solution_2_5 {
  // tag::init[]
  fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C =
          { a: A -> f(g(a)) }
  // end::init[]
}
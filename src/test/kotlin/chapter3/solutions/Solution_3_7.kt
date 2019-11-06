package chapter3.solutions

import chapter3.Cons
import chapter3.List
import chapter3.Nil

// tag::init[]
fun <A, B> foldRight(xs: List<A>, z: B, f: (A, B) -> B): B =
    when (xs) {
        is Nil -> z
        is Cons -> f(xs.head, foldRight(xs.tail, z, f))
    }

val f = { x: Int, y: List<Int> -> Cons(x, y) }
val z = Nil as List<Int>

val step1 = foldRight(List.of(1, 2, 3), z, f)
val step2 = Cons(1, foldRight(List.of(2, 3), z, f))
val step3 = Cons(1, Cons(2, foldRight(List.of(3), z, f)))
val step4 = Cons(1, Cons(2, Cons(3, foldRight(List.empty(), z, f))))
val step5: List<Int> = Cons(1, Cons(2, Cons(3, Nil)))
// end::init[]

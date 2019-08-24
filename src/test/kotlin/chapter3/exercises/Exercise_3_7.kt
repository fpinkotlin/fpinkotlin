package chapter3.exercises

import chapter3.Cons
import chapter3.List
import chapter3.Nil

// tag::init[]
fun <A, B> foldRight(xs: List<A>, z: B, f: (A, B) -> B): B = TODO()

val f = { x: Int, y: List<Int> -> Cons(x, y) }
val z = Nil as List<Int>

val step1 = chapter3.solutions.foldRight(List.of(1, 2, 3), chapter3.solutions.z, chapter3.solutions.f)
val step2 = Cons(1, chapter3.solutions.foldRight(List.of(2, 3), chapter3.solutions.z, chapter3.solutions.f))
val step3 = Cons(1, Cons(2, chapter3.solutions.foldRight(List.of(3), chapter3.solutions.z, chapter3.solutions.f)))
val step4 = Cons(1, Cons(2, Cons(3, chapter3.solutions.foldRight(List.empty(), chapter3.solutions.z, chapter3.solutions.f))))
val step5: List<Int> = Cons(1, Cons(2, Cons(3, Nil)))
// end::init[]

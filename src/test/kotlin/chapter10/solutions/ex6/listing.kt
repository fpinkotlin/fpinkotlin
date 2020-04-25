package chapter10.solutions.ex6

import chapter10.dual
import chapter10.endoMonoid
import chapter10.foldMap

//tag::init1[]
fun <A, B> foldRight(la: Sequence<A>, z: B, f: (A, B) -> B): B =
    foldMap(la, endoMonoid()) { a: A -> { b: B -> f(a, b) } }(z)
//end::init1[]

//tag::init2[]
fun <A, B> foldLeft(la: Sequence<A>, z: B, f: (B, A) -> B): B =
    foldMap(la, dual(endoMonoid())) { a: A -> { b: B -> f(b, a) } }(z)
//end::init2[]

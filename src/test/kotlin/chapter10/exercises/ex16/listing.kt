package chapter10.exercises.ex16

import arrow.Kind
import chapter10.List
import chapter10.Monoid
import chapter10.dual
import chapter10.endoMonoid
import utils.SOLUTION_HERE

interface Foldable<F> {

    fun <A, B> foldRight(fa: Kind<F, A>, z: B, f: (A, B) -> B): B =
        foldMap(fa, endoMonoid()) { a: A -> { b: B -> f(a, b) } }(z)

    fun <A, B> foldLeft(fa: Kind<F, A>, z: B, f: (B, A) -> B): B =
        foldMap(fa, dual(endoMonoid())) { a: A -> { b: B -> f(b, a) } }(z)

    fun <A, B> foldMap(fa: Kind<F, A>, m: Monoid<B>, f: (A) -> B): B =
        foldRight(fa, m.nil, { a, b -> m.combine(f(a), b) })

    //tag::init1[]
    fun <A> toList(fa: Kind<F, A>): List<A> =

        SOLUTION_HERE()
    //end::init1[]
}

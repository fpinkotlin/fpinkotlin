package chapter10.exercises.ex12

import arrow.Kind
import chapter10.Monoid

//tag::init1[]
interface Foldable<F> {

    fun <A, B> foldRight(fa: Kind<F, A>, z: B, f: (A, B) -> B): B = TODO()

    fun <A, B> foldLeft(fa: Kind<F, A>, z: B, f: (B, A) -> B): B = TODO()

    fun <A, B> foldMap(
        fa: Kind<F, A>,
        m: Monoid<B>,
        f: (A) -> B
    ): B = TODO()
}
//end::init1[]

package chapter3.exercises

import chapter3.exercises.Solution_3_7.foldRight
import chapter3.exercises.Solution_3_9.foldLeft
import chapter3.listings.List

typealias Identity<B> = (B) -> B

object Solution_3_12 {
    // tag::init[]
    fun <A, B> foldLeftR(xs: List<A>, z: B, f: (B, A) -> B): B =
            foldRight(xs, { b: B -> b }, { a, g -> { b -> g(f(b, a)) } })(z)

    fun <A, B> foldRightL(xs: List<A>, z: B, f: (A, B) -> B): B =
            foldLeft(xs, { b: B -> b }, { g, a -> { b -> g(f(a, b)) } })(z)

    //expanded example
    fun <A, B> foldLeftRLikeYouMeanIt(ls: List<A>, outerIdentity: B, combiner: (B, A) -> B): B {

        val innerIdentity: Identity<B> = { b: B -> b }

        val combinerDelayer: (A, Identity<B>) -> Identity<B> =
                { a: A, delayExec: Identity<B> -> { b: B -> delayExec(combiner(b, a)) } }

        fun go(combinerDelayer: (A, Identity<B>) -> Identity<B>): Identity<B> =
                foldRight(ls, innerIdentity, combinerDelayer)

        return go(combinerDelayer).invoke(outerIdentity)
    }
    // end::init[]
}
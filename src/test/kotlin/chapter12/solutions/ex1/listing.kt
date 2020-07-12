package chapter12.solutions.ex1

import arrow.Kind
import chapter12.Cons
import chapter12.Functor
import chapter12.List

interface Applicative<F> : Functor<F> {

    fun <A, B, C> map2(
        fa: Kind<F, A>,
        fb: Kind<F, B>,
        f: (A, B) -> C
    ): Kind<F, C>

    fun <A> unit(a: A): Kind<F, A>

    override fun <A, B> map(
        fa: Kind<F, A>,
        f: (A) -> B
    ): Kind<F, B> = //<1>
        map2(fa, unit(Unit)) { a, _ -> f(a) } // <2>

    fun <A, B> traverse(
        la: List<A>,
        f: (A) -> Kind<F, B>
    ): Kind<F, List<B>> = // <3>
        la.foldRight(
            unit(List.empty<B>()),
            { a: A, acc: Kind<F, List<B>> ->
                map2(f(a), acc) { b: B, lb: List<B> -> Cons(b, lb) }
            }
        )

    //tag::init1[]
    fun <A> sequence(lfa: List<Kind<F, A>>): Kind<F, List<A>> =
        traverse(lfa) { it }

    fun <A> replicateM(n: Int, ma: Kind<F, A>): Kind<F, List<A>> =
        sequence(List.fill(n, ma))

    fun <A, B> product(
        ma: Kind<F, A>,
        mb: Kind<F, B>
    ): Kind<F, Pair<A, B>> =
        map2(ma, mb) { a, b -> Pair(a, b) }
    //end::init1[]
}

package chapter12.sec7_3

import arrow.Kind
import chapter10.Foldable
import chapter10.None
import chapter10.Option
import chapter10.Some
import chapter12.Cons
import chapter12.Functor
import chapter12.List
import chapter12.Nil
import java.lang.Exception

interface Traverse<F> : Functor<F>, Foldable<F> {

    fun <A> toList(ta: Kind<F, A>): List<A> = TODO()

    fun <S, A, B> mapAccum(
        fa: Kind<F, A>,
        s: S,
        f: (A, S) -> Pair<B, S>
    ): Pair<Kind<F, B>, S> = TODO()

    //tag::init0[]
    fun <A, B> zip(ta: Kind<F, A>, tb: Kind<F, B>): Kind<F, Pair<A, B>> =
        mapAccum(ta, toList(tb)) { a: A, b: List<B> ->
            when (b) {
                is Cons -> (a to b.head) to b.tail
                is Nil -> throw Exception("incompatible shapes for zip")
            }
        }.first
    //end::init0[]

    //tag::init1[]
    fun <A, B> zipL(
        ta: Kind<F, A>,
        tb: Kind<F, B>
    ): Kind<F, Pair<A, Option<B>>> =
        mapAccum(ta, toList(tb)) { a: A, b: List<B> ->
            when (b) {
                is Nil -> (a to None) to Nil
                is Cons -> (a to Some(b.head)) to b.tail
            }
        }.first

    fun <A, B> zipR(
        ta: Kind<F, A>,
        tb: Kind<F, B>
    ): Kind<F, Pair<Option<A>, B>> =
        mapAccum(tb, toList(ta)) { b: B, a: List<A> ->
            when (a) {
                is Nil -> (None to b) to Nil
                is Cons -> (Some(a.head) to b) to a.tail
            }
        }.first
    //end::init1[]
}

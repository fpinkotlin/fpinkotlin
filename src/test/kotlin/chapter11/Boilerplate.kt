package chapter11

import arrow.Kind
import arrow.core.ForListK
import arrow.core.ListK
import arrow.core.ListKOf
import arrow.core.fix
import chapter10.Cons
import chapter10.List

interface Functor<F> {
    fun <A, B> map(fa: Kind<F, A>, f: (A) -> B): Kind<F, B>
}

interface Monad<F> : Functor<F> {

    fun <A> unit(a: A): Kind<F, A>
    fun <A, B> flatMap(fa: Kind<F, A>, f: (A) -> Kind<F, B>): Kind<F, B>

    override fun <A, B> map(fa: Kind<F, A>, f: (A) -> B): Kind<F, B> =
        flatMap(fa) { a -> unit(f(a)) }

    fun <A, B, C> map2(fa: Kind<F, A>, fb: Kind<F, B>, f: (A, B) -> C) =
        flatMap(fa) { a -> map(fb) { b -> f(a, b) } }

    fun <A, B> traverse(
        la: List<A>,
        f: (A) -> Kind<F, B>
    ): Kind<F, List<B>> =
        la.foldRight(
            unit(List.empty<B>()),
            { a: A, acc: Kind<F, List<B>> ->
                map2(f(a), acc) { b: B, lb: List<B> -> Cons(b, lb) }
            }
        )

    fun <A> sequence(lfa: List<Kind<F, A>>): Kind<F, List<A>> =
        lfa.foldRight(
            unit(List.empty<A>()),
            { fa: Kind<F, A>, fla: Kind<F, List<A>> ->
                map2(fa, fla) { a: A, la: List<A> -> Cons(a, la) }
            }
        )

    fun <A> replicateM(n: Int, ma: Kind<F, A>): Kind<F, List<A>> =
        when (n) {
            0 -> unit(List.empty())
            else ->
                map2(ma, replicateM(n - 1, ma)) { m: A, ml: List<A> ->
                    Cons(m, ml)
                }
        }

    fun <A, B, C> compose(
        f: (A) -> Kind<F, B>,
        g: (B) -> Kind<F, C>
    ): (A) -> Kind<F, C>

    fun <A> join(mma: Kind<F, Kind<F, A>>): Kind<F, A> =
        flatMap(mma) { ma -> ma }
}

val listKMonad = object : Monad<ForListK> {
    override fun <A> unit(a: A): ListKOf<A> = ListK.empty()

    override fun <A, B> flatMap(
        fa: ListKOf<A>,
        f: (A) -> ListKOf<B>
    ): ListKOf<B> =
        fa.fix().flatMap(f)

    override fun <A, B, C> compose(
        f: (A) -> Kind<ForListK, B>,
        g: (B) -> Kind<ForListK, C>
    ): (A) -> Kind<ForListK, C> = TODO()
}

interface StateMonad<S> : Monad<StatePartialOf<S>> {

    override fun <A> unit(a: A): StateOf<S, A>

    override fun <A, B> flatMap(
        fa: StateOf<S, A>,
        f: (A) -> StateOf<S, B>
    ): StateOf<S, B>
}

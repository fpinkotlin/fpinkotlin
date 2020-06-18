package chapter11.solutions.ex6

import arrow.Kind
import chapter10.Cons
import chapter10.List
import chapter10.Nil
import chapter11.Functor

interface Monad<F> : Functor<F> {

    fun <A> unit(a: A): Kind<F, A>
    fun <A, B> flatMap(fa: Kind<F, A>, f: (A) -> Kind<F, B>): Kind<F, B>

    override fun <A, B> map(fa: Kind<F, A>, f: (A) -> B): Kind<F, B> =
        flatMap(fa) { a -> unit(f(a)) }

    //tag::init[]
    fun <A> filterM(
        ms: List<A>,
        f: (A) -> Kind<F, Boolean>
    ): Kind<F, List<A>> =
        when (ms) {
            is Nil -> unit(Nil)
            is Cons ->
                flatMap(f(ms.head)) { succeed ->
                    if (succeed) map(filterM(ms.tail, f)) { tail ->
                        Cons(ms.head, tail)
                    } else filterM(ms.tail, f)
                }
        }
    //end::init[]
}

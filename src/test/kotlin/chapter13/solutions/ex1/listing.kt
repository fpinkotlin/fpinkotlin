package chapter13.solutions.ex1

import chapter11.Monad
import chapter13.boilerplate.free.FlatMap
import chapter13.boilerplate.free.Free
import chapter13.boilerplate.free.FreeOf
import chapter13.boilerplate.free.FreePartialOf
import chapter13.boilerplate.free.Return
import chapter13.boilerplate.free.fix

//tag::init1[]
fun <F, A, B> Free<F, A>.flatMap(f: (A) -> Free<F, B>): Free<F, B> =
    FlatMap(this, f)
fun <F, A, B> Free<F, A>.map(f: (A) -> B): Free<F, B> =
    flatMap { a -> Return<F, B>(f(a)) }
//end::init1[]

//tag::init2[]
fun <F> freeMonad() = object : Monad<FreePartialOf<F>> {
    override fun <A, B> map(
        fa: FreeOf<F, A>,
        f: (A) -> B
    ): FreeOf<F, B> =
        flatMap(fa) { a -> unit(f(a)) }

    override fun <A> unit(a: A): FreeOf<F, A> =
        Return(a)

    override fun <A, B> flatMap(
        fa: FreeOf<F, A>,
        f: (A) -> FreeOf<F, B>
    ): FreeOf<F, B> =
        fa.fix().flatMap { a -> f(a).fix() }
}
//end::init2[]

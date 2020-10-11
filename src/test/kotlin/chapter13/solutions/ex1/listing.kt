package chapter13.solutions.ex1

import arrow.Kind
import arrow.Kind2

interface Functor<F> {
    fun <A, B> map(fa: Kind<F, A>, f: (A) -> B): Kind<F, B>
}

interface Monad<F> : Functor<F> {

    fun <A> unit(a: A): Kind<F, A>

    fun <A, B> flatMap(fa: Kind<F, A>, f: (A) -> Kind<F, B>): Kind<F, B>
}

class ForFree private constructor() { companion object }
typealias FreeOf<F, A> = Kind2<ForFree, F, A>
typealias FreePartialOf<F> = Kind<ForFree, F>
@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
inline fun <F, A> FreeOf<F, A>.fix(): Free<F, A> = this as Free<F, A>

//tag::init1[]
sealed class Free<F, A> : FreeOf<F, A> {
    fun <B> flatMap(f: (A) -> Free<F, B>): Free<F, B> =
        FlatMap(this, f)
    fun <B> map(f: (A) -> B): Free<F, B> =
        flatMap { a -> Return<F, B>(f(a)) }
}
//end::init1[]

data class Return<F, A>(val a: A) : Free<F, A>()
data class Suspend<F, A>(val s: Kind<F, A>) : Free<F, A>()
data class FlatMap<F, A, B>(
    val s: Free<F, A>,
    val f: (A) -> Free<F, B>
) : Free<F, B>()

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

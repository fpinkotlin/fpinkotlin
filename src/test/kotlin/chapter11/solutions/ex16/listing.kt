package chapter11.solutions.ex16

import arrow.Kind
import chapter11.Functor

interface Monad<F> : Functor<F> {
    fun <A> unit(a: A): Kind<F, A>
    fun <A, B> flatMap(fa: Kind<F, A>, f: (A) -> Kind<F, B>): Kind<F, B>
}

//tag::init1[]
data class Id<out A>(val a: A) : IdOf<A> {
    companion object {
        fun <A> unit(a: A): Id<A> = Id(a)
    }

    fun <B> flatMap(f: (A) -> Id<B>): Id<B> = f(this.a)
    fun <B> map(f: (A) -> B): Id<B> = unit(f(this.a))
}

class ForId private constructor() {
    companion object
}

typealias IdOf<A> = Kind<ForId, A>

fun <A> IdOf<A>.fix() = this as Id<A>

fun idMonad() = object : Monad<ForId> {
    override fun <A> unit(a: A): IdOf<A> =
        Id.unit(a)

    override fun <A, B> flatMap(fa: IdOf<A>, f: (A) -> IdOf<B>): IdOf<B> =
        fa.fix().flatMap { a -> f(a).fix() }

    override fun <A, B> map(fa: IdOf<A>, f: (A) -> B): IdOf<B> =
        fa.fix().map(f)
}
//end::init1[]

fun main() {
    //tag::init2[]
    val IDM: Monad<ForId> = idMonad()
    val id: Id<String> = IDM.flatMap(Id("Hello, ")) { a: String ->
        IDM.flatMap(Id("monad!")) { b: String ->
            Id(a + b)
        }
    }.fix()
    //end::init2[]
}

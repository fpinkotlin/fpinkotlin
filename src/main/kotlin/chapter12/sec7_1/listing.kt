package chapter12.sec7_1

import arrow.Kind
import arrow.Kind2
import chapter10.Monoid
import chapter12.Functor

interface Traversable1<F> : Functor<F> {
    //tag::init1[]
    fun <G, A, B> traverse(
        fa: Kind<F, A>,
        AP: Applicative<G>,
        f: (A) -> Kind<G, B>
    ): Kind<G, Kind<F, B>>
    //end::init1[]
        = TODO()
}

//tag::init2[]
typealias ConstInt<A> = Int
//end::init2[]

interface Traversable2<F> : Functor<F> {
    //tag::init3[]
    fun <A, B> traverse(fa: Kind<F, A>, f: (A) -> Int): Int
    //end::init3[]
        = TODO()
}

interface Foldable<F> {
    //tag::init4[]
    fun <A, B> foldMap(fa: Kind<F, A>, m: Monoid<B>, f: (A) -> B): B
    //end::init4[]
        = TODO()
}

/*
//tag::init5[]
typealias Const<M, A> = M
//end::init5[]
 */


interface Applicative<F> : Functor<F> {
    fun <A> unit(a: A): Kind<F, A>
    fun <A, B, C> map2(
        fa: Kind<F, A>,
        fb: Kind<F, B>,
        f: (A, B) -> C
    ): Kind<F, C>
}

//tag::init6[]
class ForConst private constructor() { // <1>
    companion object
}

typealias ConstOf<A, T> = Kind2<ForConst, A, T> // <1>

typealias ConstPartialOf<A> = Kind<ForConst, A> // <1>

data class Const<M, out A>(val value: M) : ConstOf<M, A>  // <2>
//end::init6[]

fun <A, T> ConstOf<A, T>.fix(): Const<A, T> = this as Const<A, T>

//tag::init7[]
fun <M> monoidApplicative(m: Monoid<M>): Applicative<ConstPartialOf<M>> =
    object : Applicative<ConstPartialOf<M>> {
        //tag::ignore[]
        override fun <A, B> map(
            fa: ConstOf<M, A>,
            f: (A) -> B
        ): ConstOf<M, B> = TODO()
        //end::ignore[]

        override fun <A> unit(a: A): ConstOf<M, A> = Const(m.nil) // <1>

        override fun <A, B, C> map2(
            ma: ConstOf<M, A>,
            mb: ConstOf<M, B>,
            f: (A, B) -> C // <2>
        ): ConstOf<M, C> =
            Const(m.combine(ma.fix().value, ma.fix().value))
    }
//end::init7[]

//tag::init8[]
interface Traversable<F> : Functor<F>, Foldable<F> { // <1>

    fun <G, A, B> traverse(
        fa: Kind<F, A>,
        AP: Applicative<G>,
        f: (A) -> Kind<G, B>
    ): Kind<G, Kind<F, B>>

    override fun <A, M> foldMap(
        fa: Kind<F, A>,
        m: Monoid<M>,
        f: (A) -> M
    ): M =
        traverse(fa, monoidApplicative(m)) { a ->
            Const<M, A>(f(a))
        }.fix().value // <2>
}
//end::init8[]
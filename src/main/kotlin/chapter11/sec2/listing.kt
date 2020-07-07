package chapter11.sec2

import arrow.Kind
import chapter11.ForGen
import chapter11.Gen
import chapter11.Gen.Companion.unit
import chapter11.GenOf
import chapter11.fix
import chapter11.sec1.Functor
import chapter4.Option
import chapter9.Parser

fun <A, B> flatMap(fa: Gen<A>, f: (A) -> Gen<B>): Gen<B> = TODO()

fun <A, B> flatMap(fa: Parser<A>, f: (A) -> Parser<B>): Parser<B> = TODO()

fun <A, B> map(fa: Parser<A>, f: (A) -> B): Parser<B> = TODO()

fun <A, B> flatMap(fa: Option<A>, f: (A) -> Option<B>): Option<B> = TODO()

fun <A, B> map(fa: Option<A>, f: (A) -> B): Option<B> = TODO()

//tag::init6[]
fun <A, B> map(fa: Gen<A>, f: (A) -> B): Gen<B> =
    flatMap(fa) { a -> unit(f(a)) }
//end::init6[]

//tag::init1[]
fun <A, B, C> map2(
    fa: Gen<A>,
    fb: Gen<B>,
    f: (A, B) -> C
): Gen<C> = //<1>
    flatMap(fa) { a -> map(fb) { b -> f(a, b) } }
//end::init1[]

//tag::init2[]
fun <A, B, C> map2(
    fa: Parser<A>,
    fb: Parser<B>,
    f: (A, B) -> C
): Parser<C> = //<2>
    flatMap(fa) { a -> map(fb) { b -> f(a, b) } }
//end::init2[]

//tag::init3[]
fun <A, B, C> map2(
    fa: Option<A>,
    fb: Option<B>,
    f: (A, B) -> C
): Option<C> = //<3>
    flatMap(fa) { a -> map(fb) { b -> f(a, b) } }
//end::init3[]

//tag::init4[]
interface Mon<F> { // <1>
    //tag::init5[]

    fun <A, B> map(fa: Kind<F, A>, f: (A) -> B): Kind<F, B>

    fun <A, B> flatMap(fa: Kind<F, A>, f: (A) -> Kind<F, B>): Kind<F, B>
    //end::init5[]

    fun <A, B, C> map2(
        fa: Kind<F, A>, // <2>
        fb: Kind<F, B>, // <3>
        f: (A, B) -> C
    ): Kind<F, C> =
        flatMap(fa) { a -> map(fb) { b -> f(a, b) } } // <4>
}
//end::init4[]

//tag::init7[]
interface Monad<F> : Functor<F> { // <1>

    fun <A> unit(a: A): Kind<F, A>

    fun <A, B> flatMap(fa: Kind<F, A>, f: (A) -> Kind<F, B>): Kind<F, B>

    override fun <A, B> map(
        fa: Kind<F, A>,
        f: (A) -> B
    ): Kind<F, B> = //<2>
        flatMap(fa) { a -> unit(f(a)) }

    fun <A, B, C> map2(
        fa: Kind<F, A>,
        fb: Kind<F, B>,
        f: (A, B) -> C
    ): Kind<F, C> =
        flatMap(fa) { a -> map(fb) { b -> f(a, b) } }
}
//end::init7[]

//tag::init8[]
object Monads {

    val genMonad = object : Monad<ForGen> { // <1>

        override fun <A> unit(a: A): GenOf<A> = Gen.unit(a) // <2>

        override fun <A, B> flatMap(
            fa: GenOf<A>,
            f: (A) -> GenOf<B>
        ): GenOf<B> =
            fa.fix().flatMap { a: A -> f(a).fix() } // <3>
    }
}
//end::init8[]

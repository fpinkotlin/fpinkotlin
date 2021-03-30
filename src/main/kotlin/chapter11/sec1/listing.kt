package chapter11.sec1

import arrow.Kind
import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right
import arrow.core.Left
import arrow.core.Right
import chapter10.ForList
import chapter10.List
import chapter10.ListOf
import chapter10.fix
import chapter4.Option
import chapter7.sec3.Par
import chapter7.sec3.Pars
import chapter8.Gen
import chapter9.Parser

//tag::init1[]
fun <A, B> map(ga: Option<A>, f: (A) -> B): Option<B>
//end::init1[]
    = TODO()

//tag::init2[]
fun <A, B> map(ga: Gen<A>, f: (A) -> B): Gen<B>
//end::init2[]
    = TODO()

//tag::init3[]
fun <A, B> map(ga: Parser<A>, f: (A) -> B): Parser<B>
//end::init3[]
    = TODO()

fun <A, B> map(pa: Par<A>, f: (A) -> B): Par<B> = TODO()

//tag::init4[]
interface Functor<F> {
    //tag::map[]
    fun <A, B> map(fa: Kind<F, A>, f: (A) -> B): Kind<F, B>
    //end::map[]
    //tag::distribute[]

    fun <A, B> distribute(
        fab: Kind<F, Pair<A, B>>
    ): Pair<Kind<F, A>, Kind<F, B>> =
        map(fab) { it.first } to map(fab) { it.second }
    //end::distribute[]
    //tag::codistribute[]

    fun <A, B> codistribute(
        e: Either<Kind<F, A>, Kind<F, B>>
    ): Kind<F, Either<A, B>> =
        when (e) {
            is Left -> map(e.a) { Left(it) }
            is Right -> map(e.b) { Right(it) }
        }
    //end::codistribute[]
}
//end::init4[]

fun <A, B> List<A>.map(f: (A) -> B): List<B> = TODO()

//tag::init5[]
val listFunctor = object : Functor<ForList> {
    override fun <A, B> map(fa: ListOf<A>, f: (A) -> B): ListOf<B> =
        fa.fix().map(f)
}
//end::init5[]

val listing = {
    val x = Pars.unit("a")
    //tag::init6[]
    map(x) { a -> a } == x
    //end::init6[]
}

interface Functor2<F> {
    //tag::distribute2[]
    fun <A, B> distribute(
        fab: Kind<F, Pair<A, B>>
    ): Pair<Kind<F, A>, Kind<F, B>>
    //end::distribute2[]
        = TODO()

    //tag::codistribute2[]
    fun <A, B> codistribute(
        e: Either<Kind<F, A>, Kind<F, B>>
    ): Kind<F, Either<A, B>>
    //end::codistribute2[]
        = TODO()
}

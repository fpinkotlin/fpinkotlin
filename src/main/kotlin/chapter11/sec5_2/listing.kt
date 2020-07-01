package chapter11.sec5_2

import arrow.Kind
import arrow.Kind2
import arrow.core.ForOption
import arrow.core.Option
import arrow.core.extensions.list.foldable.foldLeft
import arrow.core.extensions.option.monad.monad
import arrow.core.fix
import arrow.typeclasses.MonadFx
import arrow.typeclasses.MonadSyntax
import chapter11.sec2.Monad

//tag::init1[]
data class State<S, out A>(val run: (S) -> Pair<A, S>) : StateOf<S, A>
//end::init1[]
{

    companion object {
        fun <S, A> unit(a: A): State<S, A> =
            State { s: S -> Pair(a, s) }
    }

    fun <B> map(f: (A) -> B): State<S, B> =
        flatMap { a: A -> unit<S, B>(f(a)) }

    fun <B> flatMap(f: (A) -> State<S, B>): State<S, B> =
        State { s1: S ->
            val (a: A, s2: S) = this.run(s1)
            f(a).run(s2)
        }

    //tag::init5[]
    fun <S> getState(): State<S, S> = State { s -> Pair(s, s) }

    fun <S> setState(s: S): State<S, Unit> = State { Pair(Unit, s) }
    //end::init5[]
}

//tag::init2[]
sealed class ForState private constructor() { // <1>
    companion object
}

typealias StateOf<S, A> = Kind2<ForState, S, A> // <2>

fun <S, A> StateOf<S, A>.fix() = this as State<S, A> // <3>

typealias StatePartialOf<S> = Kind<ForState, S> // <4>

interface StateMonad<S> : Monad<StatePartialOf<S>> { // <5>

    override fun <A> unit(a: A): StateOf<S, A> // <6>

    override fun <A, B> flatMap(
        fa: StateOf<S, A>,
        f: (A) -> StateOf<S, B>
    ): StateOf<S, B> // <6>
}
//end::init2[]

//tag::init3[]
interface Kind<out F, out A>
typealias Kind2<F, A, B> = Kind<Kind<F, A>, B>
typealias Kind3<F, A, B, C> = Kind<Kind2<F, A, B>, C>
//end::init3[]

//tag::init4[]
val intStateMonad: StateMonad<Int> = object : StateMonad<Int> {
    override fun <A> unit(a: A): StateOf<Int, A> =
        State { s -> Pair(a, s) }

    override fun <A, B> flatMap(
        fa: StateOf<Int, A>,
        f: (A) -> StateOf<Int, B>
    ): StateOf<Int, B> =
        fa.fix().flatMap { a -> f(a).fix() }
}
//end::init4[]

//tag::init6[]
val F = intStateMonad

fun <A> zipWithIndex(la: List<A>): List<Pair<Int, A>> =
    la.foldLeft(F.unit(emptyList<Pair<Int, A>>())) { acc, a ->
        acc.fix().flatMap { xs ->
            acc.fix().getState<Int>().flatMap { n ->
                acc.fix().setState(n + 1).map { _ ->
                    listOf(Pair(n, a)) + xs
                }
            }
        }
    }.fix().run(0).first.reversed()
//end::init6[]

/*
//tag::init7[]
...
{ acc: StateOf<Int, List<Pair<Int, A>>>, a: A ->
    acc.fx {
        val (xs) = acc
        val (n) = acc.getState()
        val (_) = acc.setState(n + 1)
        listOf(Pair(n, a)) + xs
    }
}
...
//end::init7[]
 */

fun main() {
    println(zipWithIndex(listOf(1, 2, 3, 4, 5)))
}
package chapter11

import arrow.Kind
import arrow.Kind2
import arrow.core.ForListK
import arrow.core.ListK
import arrow.core.ListKOf
import arrow.core.fix
import arrow.higherkind
import chapter10.Cons
import chapter10.ForList
import chapter10.List
import chapter10.ListOf
import chapter10.fix
import chapter8.RNG
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

@higherkind
data class Gen<A>(val sample: State<RNG, A>) : GenOf<A> {
    companion object {
        fun <A> unit(a: A): Gen<A> = Gen(State.unit(a))
        fun string(): Gen<String> = TODO()
        fun double(rng: IntRange): Gen<Double> = TODO()
        fun choose(start: Int, end: Int): Gen<Int> = TODO()
    }

    fun <B> flatMap(f: (A) -> Gen<B>): Gen<B> = TODO()
    fun <B> map(f: (A) -> B): Gen<B> = TODO()
}

@higherkind
class Par<A>(val run: (ExecutorService) -> Future<A>) : ParOf<A> {
    companion object {
        fun <A> unit(a: A): Par<A> = TODO()

        fun <A> lazyUnit(a: () -> A): Par<A> = TODO()
    }

    fun <B> flatMap(f: (A) -> Par<B>): Par<B> = TODO()
}

class ForState private constructor() {
    companion object
}
typealias StateOf<S, A> = Kind2<ForState, S, A>
typealias StatePartialOf<S> = Kind<ForState, S>

inline fun <S, A> StateOf<S, A>.fix(): State<S, A> =
    this as State<S, A>

data class State<S, out A>(val run: (S) -> Pair<A, S>) : StateOf<S, A> {

    companion object {
        fun <S, A> unit(a: A): State<S, A> =
            State { s: S -> a to s }

        fun <S> get(): State<S, S> =
            State { s -> s to s }

        fun <S> set(s: S): State<S, Unit> =
            State { Unit to s }
    }

    fun <B> map(f: (A) -> B): State<S, B> =
        flatMap { a: A -> unit<S, B>(f(a)) }

    fun <B> flatMap(f: (A) -> State<S, B>): State<S, B> =
        State { s1: S ->
            val (a: A, s2: S) = this.run(s1)
            f(a).run(s2)
        }
}

interface Functor<F> {
    fun <A, B> map(fa: Kind<F, A>, f: (A) -> B): Kind<F, B>
}

//tag::init1[]
interface Monad<F> : Functor<F> {

    fun <A> unit(a: A): Kind<F, A>

    fun <A, B> flatMap(fa: Kind<F, A>, f: (A) -> Kind<F, B>): Kind<F, B>

    override fun <A, B> map(fa: Kind<F, A>, f: (A) -> B): Kind<F, B> =
        flatMap(fa) { a -> unit(f(a)) }

    //tag::ignore[]
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
    ): (A) -> Kind<F, C> =
        { a -> flatMap(f(a)) { b -> g(b) } }

    fun <A> join(mma: Kind<F, Kind<F, A>>): Kind<F, A> =
        flatMap(mma) { ma -> ma }
    //end::ignore[]
}
//end::init1[]

val listKMonad = object : Monad<ForListK> {
    override fun <A> unit(a: A): ListKOf<A> = ListK.empty()

    override fun <A, B> flatMap(
        fa: ListKOf<A>,
        f: (A) -> ListKOf<B>
    ): ListKOf<B> =
        fa.fix().flatMap(f)
}

val listMonad = object : Monad<ForList> {
    override fun <A> unit(a: A): ListOf<A> = List.of(a)

    override fun <A, B> flatMap(
        fa: ListOf<A>,
        f: (A) -> ListOf<B>
    ): ListOf<B> =
        fa.fix().flatMap { a -> f(a).fix() }
}

typealias StateMonad<S> = Monad<StatePartialOf<S>>

package chapter12.sec7_2

import arrow.Kind
import chapter10.Foldable
import chapter11.Monad
import chapter11.State
import chapter11.StateOf
import chapter11.StatePartialOf
import chapter11.fix
import chapter12.Cons
import chapter12.Functor
import chapter12.List
import chapter12.Nil

interface Applicative<F> : Functor<F> {
    fun <A> unit(a: A): Kind<F, A>
    fun <A, B, C> map2(
        fa: Kind<F, A>,
        fb: Kind<F, B>,
        f: (A, B) -> C
    ): Kind<F, C>
}

//tag::init1[]
typealias StateMonad<S> = Monad<StatePartialOf<S>>

fun <S> stateMonad() = object : StateMonad<S> {

    override fun <A> unit(a: A): StateOf<S, A> =
        State { s -> a to s }

    override fun <A, B> flatMap(
        fa: StateOf<S, A>,
        f: (A) -> StateOf<S, B>
    ): StateOf<S, B> =
        fa.fix().flatMap { f(it).fix() }

    override fun <A, B, C> compose(
        f: (A) -> StateOf<S, B>,
        g: (B) -> StateOf<S, C>
    ): (A) -> StateOf<S, C> =
        { a -> join(map(f(a), g)) }
}
//end::init1[]

//tag::init2[]
fun <S> stateMonadApplicative(m: StateMonad<S>) =
    object : Applicative<StatePartialOf<S>> {

        override fun <A> unit(a: A): Kind<StatePartialOf<S>, A> =
            m.unit(a) // <1>

        override fun <A, B, C> map2(
            fa: Kind<StatePartialOf<S>, A>,
            fb: Kind<StatePartialOf<S>, B>,
            f: (A, B) -> C
        ): Kind<StatePartialOf<S>, C> =
            m.map2(fa, fb, f) // <2>

        override fun <A, B> map(
            fa: Kind<StatePartialOf<S>, A>,
            f: (A) -> B
        ): Kind<StatePartialOf<S>, B> =
            m.map(fa, f) // <3>
    }
//end::init2[]

interface Traversable<F> : Functor<F>, Foldable<F> {

    fun <G, A, B> traverse(
        fa: Kind<F, A>,
        AP: Applicative<G>,
        f: (A) -> Kind<G, B>
    ): Kind<G, Kind<F, B>>

    //tag::init3[]
    fun <S, A, B> traverseS(
        fa: Kind<F, A>,
        f: (A) -> State<S, B>
    ): State<S, Kind<F, B>> =
        traverse(
            fa,
            stateMonadApplicative(stateMonad<S>())
        ) { a -> f(a).fix() }.fix()
    //end::init3[]

    //tag::init4[]
    fun <A> zipWithIndex(ta: Kind<F, A>): Kind<F, Pair<A, Int>> =
        traverseS(ta) { a: A ->
            State.get<Int>().flatMap { s: Int -> // <1>
                State.set(s + 1).map { _ -> // <2>
                    a to s
                }
            }
        }.run(0).first // <3>
    //end::init4[]

    //tag::init5[]
    fun <A> toList(ta: Kind<F, A>): List<A> =
        traverseS(ta) { a: A ->
            State.get<List<A>>().flatMap { la -> // <1>
                State.set<List<A>>(Cons(a, la)).map { _ -> // <2>
                    Unit
                }
            }
        }.run(Nil).second.reverse() // <3>
    //end::init5[]
}

interface Traversable2<F> : Functor<F>, Foldable<F> {

    fun <S, A, B> traverseS(
        fa: Kind<F, A>,
        f: (A) -> State<S, B>
    ): State<S, Kind<F, B>> = TODO()

    //tag::init6[]
    fun <S, A, B> mapAccum(
        fa: Kind<F, A>,
        s: S,
        f: (A, S) -> Pair<B, S>
    ): Pair<Kind<F, B>, S> =
        traverseS(fa) { a: A ->
            State.get<S>().flatMap { s1 ->
                val (b, s2) = f(a, s1)
                State.set(s2).map { _ -> b }
            }
        }.run(s)

    fun <A> zipWithIndex(ta: Kind<F, A>): Kind<F, Pair<A, Int>> =
        mapAccum(ta, 0) { a, s ->
            (a to s) to (s + 1)
        }.first

    fun <A> toList(ta: Kind<F, A>): List<A> =
        mapAccum(ta, Nil) { a: A, s: List<A> ->
            Unit to Cons(a, s)
        }.second.reverse()
    //end::init6[]
}

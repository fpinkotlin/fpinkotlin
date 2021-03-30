package chapter12.solutions.ex16

import arrow.Kind
import chapter10.Foldable
import chapter11.State
import chapter11.fix
import chapter12.Functor
import chapter12.sec7_2.Applicative
import chapter12.sec7_2.stateMonad
import chapter12.sec7_2.stateMonadApplicative

interface Traversable<F> : Functor<F>, Foldable<F> {

    fun <G, A, B> traverse(
        fa: Kind<F, A>,
        AG: Applicative<G>,
        f: (A) -> Kind<G, B>
    ): Kind<G, Kind<F, B>> = TODO()

    fun <S, A, B> traverseS(
        fa: Kind<F, A>,
        f: (A) -> State<S, B>
    ): State<S, Kind<F, B>> =
        traverse(
            fa,
            stateMonadApplicative(stateMonad<S>())
        ) { a -> f(a).fix() }.fix()

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

    //tag::init[]
    fun <A, B> foldLeft(fa: Kind<F, A>, z: B, f: (B, A) -> B): B =
        mapAccum(fa, z) { a, b ->
            Unit to f(b, a)
        }.second
    //end::init[]
}
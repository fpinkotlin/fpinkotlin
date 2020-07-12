package chapter12.solutions.ex5

import chapter12.EitherMonad
import chapter12.EitherOf
import chapter12.Left
import chapter12.Right
import chapter12.fix

//tag::init1[]
fun <E> eitherMonad() = object : EitherMonad<E> {

    override fun <A> unit(a: A): EitherOf<E, A> = Right(a)

    override fun <A, B> flatMap(
        fa: EitherOf<E, A>,
        f: (A) -> EitherOf<E, B>
    ): EitherOf<E, B> =
        when (val ei = fa.fix()) {
            is Right -> f(ei.value)
            is Left -> ei
        }
}
//end::init1[]

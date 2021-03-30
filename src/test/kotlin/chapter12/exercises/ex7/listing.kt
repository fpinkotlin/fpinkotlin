package chapter12.exercises.ex7

import arrow.Kind
import chapter11.Monad
import utils.SOLUTION_HERE

interface Listing<F, A> : Monad<F> {

    val fa: Kind<F, A>
    val fb: Kind<F, A>
    val f: (A, A) -> Kind<F, A>
    val ka: (A) -> Kind<F, A>

    fun listing1() {

        map2(unit(Unit), fa) { _, a -> a }
        SOLUTION_HERE("prove me")
        fa == fa

        map2(fa, unit(Unit)) { a, _ -> a }
        SOLUTION_HERE("prove me")
        fa == fa
    }
}

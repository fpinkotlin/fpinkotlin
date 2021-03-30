package chapter11.exercises.ex13

import arrow.Kind
import chapter11.Monad
import utils.SOLUTION_HERE

interface Listing<F, A> : Monad<F> {

    //tag::initA[]
    val f: (A) -> Kind<F, A>
    val g: (A) -> Kind<F, A>
    val x: Kind<F, A>
    val y: Kind<F, Kind<F, Kind<F, A>>>
    val z: (Kind<F, Kind<F, A>>) -> Kind<F, Kind<F, A>>
    //end::initA[]

    fun associative() {
        //tag::init1[]
        flatMap(flatMap(x, f), g) ==
            flatMap(x) { a -> flatMap(f(a), g) }
        //end::init1[]

        SOLUTION_HERE(
            "Express in terms of join, map and unit using substitution"
        )

        //tag::init5[]
        join(unit(x)) ==
            join(map(x) { unit(it) })
        //end::init5[]
    }
}

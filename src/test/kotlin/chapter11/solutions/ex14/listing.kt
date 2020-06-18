package chapter11.solutions.ex14

import arrow.Kind
import chapter11.Monad

interface Listing<F, A> : Monad<F> {

    //tag::initA[]
    val f: (A) -> Kind<F, A>
    val g: (A) -> Kind<F, A>
    val x: Kind<F, A>
    val y: Kind<F, Kind<F, Kind<F, A>>>
    val z: (Kind<F, Kind<F, A>>) -> Kind<F, Kind<F, A>>
    //end::initA[]

    fun listing() {
        //tag::init1[]
        flatMap(flatMap(x, f), g) ==
            flatMap(x) { a -> flatMap(f(a), g) }
        //end::init1[]

        //tag::init2[]
        flatMap(flatMap(y, z)) { it } ==
            flatMap(y) { a -> flatMap(z(a)) { it } }
        //end::init2[]

        //tag::init3[]
        join(join(y)) ==
            flatMap(y) { a -> join(a) }
        //end::init3[]

        //tag::init4[]
        join(join(y)) ==
            join(map(y) { a: Kind<F, Kind<F, A>> -> join(a) })
        //end::init4[]

        //tag::init5[]
        join(unit(x)) ==
            join(map(x) { x: A -> unit(x) })
        //end::init5[]
    }
}
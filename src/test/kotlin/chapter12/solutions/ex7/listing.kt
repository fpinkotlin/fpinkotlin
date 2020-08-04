package chapter12.solutions.ex7

import arrow.Kind
import chapter11.Monad

interface Listing<F, A> : Monad<F> {

    val fa: Kind<F, A>
    val fb: Kind<F, A>
    val f: (A, A) -> Kind<F, A>
    val ka: (A) -> Kind<F, A>

    fun listing1() {
        //tag::init1[]
        map2(unit(Unit), fa) { _, a -> a }
        //end::init1[]

        //tag::init2[]
        flatMap(fa) { a -> map(fb) { b -> f(a, b) } }
        //end::init2[]

        //tag::init3[]
        flatMap(unit(Unit)) { u -> map(fa) { a -> a } } == fa
        //end::init3[]

        //tag::init4[]
        flatMap(unit(Unit)) { fa } == fa
        //end::init4[]

        //tag::init5[]
        compose({ _: A -> unit(Unit) }, { _ -> fa }) == { _: A -> fa }
        //end::init5[]

        val unit = { a: A -> unit(a) }
        //tag::init6[]
        compose(unit, ka) == ka
        //end::init6[]

        //tag::init7[]
        { _: A -> fa } == { _: A -> fa }
        //end::init7[]

        //tag::init8[]
        fa == fa
        //end::init8[]

        //tag::init9[]
        map2(fa, unit(Unit)) { a, _ -> a }
        //end::init9[]

        //tag::init10[]
        flatMap(fa) { a -> map(unit(Unit)) { u -> a } } == fa

        flatMap(fa) { a -> unit(a) } == fa

        compose({ _: A -> fa }, { _: A -> unit(Unit) }) == { _: A -> fa }
        //end::init10[]

        //tag::init11[]
        compose(ka, unit) == ka
        //end::init11[]

        //tag::init12[]
        { _: A -> fa } == { _: A -> fa }

        fa == fa
        //end::init12[]
    }
}

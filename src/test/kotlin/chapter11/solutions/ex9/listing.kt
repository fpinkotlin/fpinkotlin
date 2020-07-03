package chapter11.solutions.ex9

import arrow.Kind
import chapter11.Monad

interface Listing<F, A> : Monad<F> {

    //tag::init0[]
    val f: (A) -> Kind<F, A>
    val x: Kind<F, A>
    val v: A
    //end::init0[]

    fun listing() {

        val left1 =
            //tag::initl1[]
            compose(f, { a: A -> unit(a) })(v) == f(v)
        //end::initl1[]
        val left2 =
            //tag::initl2[]
            { b: A -> flatMap(f(b), { a: A -> unit(a) }) }(v) == f(v)
        //end::initl2[]
        val left3 =
            //tag::initl3[]
            flatMap(f(v)) { a: A -> unit(a) } == f(v)
        //end::initl3[]
        val left4 =
            //tag::initl4[]
            flatMap(x) { a: A -> unit(a) } == x
        //end::initl4[]

        val right1 =
            //tag::initr1[]
            compose({ a: A -> unit(a) }, f)(v) == f(v)
        //end::initr1[]
        val right2 =
            //tag::initr2[]
            { b: A -> flatMap({ a: A -> unit(a) }(b), f) }(v) == f(v)
        //end::initr2[]
        val right3 =
            //tag::initr3[]
            { b: A -> flatMap(unit(b), f) }(v) == f(v)
        //end::initr3[]
        val right4 =
            //tag::initr4[]
            flatMap(unit(v), f) == f(v)
        //end::initr4[]

        //tag::init[]
        flatMap(x) { a -> unit(a) } == x
        flatMap(unit(v), f) == f(v)
        //end::init[]
    }
}

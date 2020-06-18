package chapter11.solutions.ex10

import arrow.Kind
import chapter11.Monad

interface Listing<F, A> : Monad<F> {

    //tag::init0[]
    val fx: (A) -> Kind<F, A>
    val x: Kind<F, A>
    val v: A
    //end::init0[]

    fun listing() {

        val left1 =
            //tag::initl1[]
            compose(fx, { a: A -> unit(a) }) == fx(v)
        //end::initl1[]
        val left2 =
            //tag::initl2[]
            { b: A -> flatMap(fx(b), { a: A -> unit(a) }) } == fx(v)
        //end::initl2[]
        val left3 =
            //tag::initl3[]
            flatMap(fx(v)) { a: A -> unit(a) } == fx(v)
        //end::initl3[]
        val left4 =
            //tag::initl4[]
            flatMap(x) { a: A -> unit(a) } == x
        //end::initl4[]

        val right1 =
            //tag::initr1[]
            compose({ a: A -> unit(a) }, fx) == fx(v)
        //end::initr1[]
        val right2 =
            //tag::initr2[]
            { b: A -> flatMap({ a: A -> unit(a) }(b), fx) } == fx(v)
        //end::initr2[]
        val right3 =
            //tag::initr3[]
            { b: A -> flatMap(unit(b), fx) } == fx(v)
        //end::initr3[]
        val right4 =
            //tag::initr4[]
            flatMap(unit(v), fx) == fx(v)
        //end::initr4[]
        val right5 =
            //tag::initr5[]
            flatMap(unit(v), fx) == x
        //end::initr5[]

        val result =
            //tag::init[]
            flatMap(x) { a -> unit(a) } ==
                flatMap(unit(v), fx)
        //end::init[]
    }
}
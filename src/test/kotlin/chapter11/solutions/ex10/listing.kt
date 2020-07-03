package chapter11.solutions.ex10

import arrow.core.ForOption
import arrow.core.None
import arrow.core.Some
import chapter11.Monad

interface Listing<A> : Monad<ForOption> {

    val v: A

    fun exercise() {

        //left identity for None
        //tag::init1[]
        flatMap(None) { a: A -> Some(a) } == None
        None == None
        //end::init1[]

        //left identity for Some
        //tag::init2[]
        flatMap(Some(v)) { a: A -> Some(a) } == Some(v)
        Some(v) == Some(v)
        //end::init2[]

        //right identity for None
        //tag::init3[]
        flatMap(Some(None)) { a -> Some(a) } == Some(None)
        Some(None) == Some(None)
        //end::init3[]

        //right identity for Some
        //tag::init4[]
        flatMap(Some(Some(v))) { a -> Some(a) } == Some(Some(v))
        Some(Some(v)) == Some(Some(v))
        //end::init4[]
    }
}

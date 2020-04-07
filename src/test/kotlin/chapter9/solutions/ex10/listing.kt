package chapter9.solutions.ex10

import chapter9.solutions.final.Parser

interface Parsers {
    //tag::init1[]
    fun <A> furthest(pa: Parser<A>): Parser<A>
    //end::init1[]

    //tag::init2[]
    fun <A> latest(pa: Parser<A>): Parser<A>
    //end::init2[]
}

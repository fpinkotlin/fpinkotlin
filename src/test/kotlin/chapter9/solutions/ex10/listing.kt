package chapter9.solutions.ex10

import chapter9.solutions.final.Parser

interface Parsers {
    //tag::init[]
    /**
     * In the event of an error, returns the error that occurred after
     * consuming the most number of characters.
     */
    fun <A> furthest(pa: Parser<A>): Parser<A>

    /**
     * In the event of an error, returns the error that occurred most
     * recently.
     */
    fun <A> latest(pa: Parser<A>): Parser<A>
    //end::init[]
}
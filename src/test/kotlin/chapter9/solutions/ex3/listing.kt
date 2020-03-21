package chapter9.solutions.ex3

import chapter9.ParseError
import chapter9.Parser
import chapter9.Parsers

abstract class Listing : Parsers<ParseError> {

    init {
        //tag::init1[]
        fun <A> many(pa: Parser<A>): Parser<List<A>> =
            map2(pa, many(pa)) { a, la -> listOf(a) + la } or
                succeed(emptyList())
        //end::init1[]
    }
}

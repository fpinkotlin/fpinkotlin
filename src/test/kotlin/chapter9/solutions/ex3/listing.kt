package chapter9.solutions.ex3

import chapter9.solutions.final.ParseError
import chapter9.solutions.final.Parser
import chapter9.solutions.final.ParserDsl

abstract class Listing : ParserDsl<ParseError>() {

    fun <A, B, C> map2(
        pa: Parser<A>,
        pb: Parser<B>,
        f: (A, B) -> C
    ): Parser<C> = TODO()

    init {
        //tag::init1[]
        fun <A> many(pa: Parser<A>): Parser<List<A>> =
            map2(pa, many(pa)) { a, la ->
                listOf(a) + la
            } or succeed(emptyList())
        //end::init1[]
    }
}

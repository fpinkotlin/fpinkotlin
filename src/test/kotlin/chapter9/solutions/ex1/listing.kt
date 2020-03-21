package chapter9.solutions.ex1

import chapter9.ParseError
import chapter9.Parser
import chapter9.Parsers

abstract class Listing : Parsers<ParseError> {

    //tag::init1[]
    override fun <A, B, C> map2(
        pa: Parser<A>,
        pb: Parser<B>,
        f: (A, B) -> C
    ): Parser<C> =
        (pa product pb).map { pab ->
            f(pab.first, pab.second)
        }
    //end::init1[]

    //tag::init2[]
    override fun <A> many1(p: Parser<A>): Parser<List<A>> =
        map2(p, p.many()) { a, b -> listOf(a) + b }
    //end::init2[]
}

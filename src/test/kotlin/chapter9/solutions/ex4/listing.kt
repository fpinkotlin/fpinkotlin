package chapter9.solutions.ex4

import chapter9.ParseError
import chapter9.Parser
import chapter9.Parsers

abstract class Listing : Parsers<ParseError> {
    init {
        //tag::init1[]
        fun <A> listOfN(n: Int, pa: Parser<A>): Parser<List<A>> =
            if (n > 0)
                map2(pa, listOfN(n - 1, pa)) { a, la ->
                    listOf(a) + la
                }
            else succeed(emptyList())
        //end::init1[]
    }
}

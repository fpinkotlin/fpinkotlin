package chapter9.solutions.ex7

import chapter9.ParseError
import chapter9.Parser
import chapter9.Parsers

abstract class Listing : Parsers<ParseError> {

    init {
        //tag::init1[]
        fun <A, B> map(pa: Parser<A>, f: (A) -> B): Parser<B> =
            pa.flatMap { a -> succeed(f(a)) }
        //end::init1[]
    }
}

package chapter9.solutions.ex8

import chapter9.solutions.final.ParseError
import chapter9.solutions.final.Parser
import chapter9.solutions.final.ParserDsl

abstract class Listing : ParserDsl<ParseError>() {

    init {
        //tag::init1[]
        fun <A, B> map(pa: Parser<A>, f: (A) -> B): Parser<B> =
            pa.flatMap { a -> succeed(f(a)) }
        //end::init1[]
    }
}

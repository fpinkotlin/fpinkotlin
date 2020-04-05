package chapter9.sec3_1

import chapter9.sec2_2.ParseError
import chapter9.sec2_2.Parser
import chapter9.sec2_2.Parsers

abstract class Listing : Parsers<ParseError> {
    //tag::init1[]
    fun <A, B> flatMap(pa: Parser<A>, f: (A) -> Parser<B>): Parser<B>
    //end::init1[]
        = TODO()

    //tag::init2[]
    fun regex(r: String): Parser<String> = TODO()
    //end::init2[]
}

package chapter9.sec2_4

import chapter9.sec2_2.ParseError
import chapter9.sec2_2.Parser
import chapter9.sec2_2.Parsers

abstract class Listing : Parsers<ParseError> {
    //tag::init1[]
    fun <A, B> product(
        pa: Parser<A>,
        pb: () -> Parser<B>
    ): Parser<Pair<A, B>> = TODO()

    fun <A, B, C> map2(
        pa: Parser<A>,
        pb: () -> Parser<B>,
        f: (A, B) -> C
    ): Parser<C> =
        product(pa, pb).map { (a, b) -> f(a, b) }
    //end::init1[]

    //tag::init2[]
    fun <A> or(pa: Parser<A>, pb: () -> Parser<A>): Parser<A> = TODO()
    //end::init2[]
}

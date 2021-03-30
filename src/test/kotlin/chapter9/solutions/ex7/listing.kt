package chapter9.solutions.ex7

import chapter9.solutions.final.ParseError
import chapter9.solutions.final.Parser
import chapter9.solutions.final.ParserDsl

abstract class Listing : ParserDsl<ParseError>() {
    init {

        //tag::init1[]
        fun <A, B> product(
            pa: Parser<A>,
            pb: Parser<B>
        ): Parser<Pair<A, B>> =
            pa.flatMap { a -> pb.map { b -> a to b } }
        //end::init1[]

        //tag::init2[]
        fun <A, B, C> map2(
            pa: Parser<A>,
            pb: Parser<B>,
            f: (A, B) -> C
        ): Parser<C> =
            pa.flatMap { a -> pb.map { b -> f(a, b) } }
        //end::init2[]
    }
}

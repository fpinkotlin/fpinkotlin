package chapter9.exercises.ex7

import chapter9.solutions.final.ParseError
import chapter9.solutions.final.Parser
import chapter9.solutions.final.ParserDsl
import utils.SOLUTION_HERE

abstract class Listing : ParserDsl<ParseError>() {
    init {

        //tag::init1[]
        fun <A, B> product(
            pa: Parser<A>,
            pb: Parser<B>
        ): Parser<Pair<A, B>> =

            SOLUTION_HERE()
        //end::init1[]

        //tag::init2[]
        fun <A, B, C> map2(
            pa: Parser<A>,
            pb: Parser<B>,
            f: (A, B) -> C
        ): Parser<C> =

            SOLUTION_HERE()
        //end::init2[]
    }
}

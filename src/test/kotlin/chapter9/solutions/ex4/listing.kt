package chapter9.solutions.ex4

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
        fun <A> listOfN(n: Int, pa: Parser<A>): Parser<List<A>> =
            if (n > 0)
                map2(pa, listOfN(n - 1, pa)) { a, la ->
                    listOf(a) + la
                }
            else succeed(emptyList())
        //end::init1[]
    }
}

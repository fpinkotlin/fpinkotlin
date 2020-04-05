package chapter9.solutions.ex13

import chapter9.solutions.final.Location
import chapter9.solutions.final.Parser
import chapter9.solutions.final.Parsers
import chapter9.solutions.final.Result

abstract class ParsersImpl<PE> : Parsers<PE>() {
    //tag::init[]
    override fun <A> run(p: Parser<A>, input: String): Result<A> =
        p(Location(input))
    //end::init[]
}
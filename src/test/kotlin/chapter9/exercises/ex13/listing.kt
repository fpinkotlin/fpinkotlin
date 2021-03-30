package chapter9.exercises.ex13

import chapter9.solutions.final.Parser
import chapter9.solutions.final.Parsers
import chapter9.solutions.final.Result
import utils.SOLUTION_HERE

abstract class ParsersImpl<PE> : Parsers<PE>() {
    //tag::init[]
    override fun <A> run(p: Parser<A>, input: String): Result<A> =

        SOLUTION_HERE()
    //end::init[]
}
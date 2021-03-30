package chapter9.exercises.ex6

import chapter9.solutions.final.ParseError
import chapter9.solutions.final.Parser
import chapter9.solutions.final.ParserDsl
import utils.SOLUTION_HERE

abstract class Listing : ParserDsl<ParseError>() {
    init {

        //tag::init1[]
        val parser: Parser<Int> =

            SOLUTION_HERE()
        //end::init1[]
    }
}

package chapter9.solutions.ex6

import chapter9.solutions.final.ParseError
import chapter9.solutions.final.Parser
import chapter9.solutions.final.ParserDsl

abstract class Listing : ParserDsl<ParseError>() {
    init {

        //tag::init1[]
        val parser: Parser<Int> = regex("[0-9]+")
            .flatMap { digit: String ->
                val reps = digit.toInt()
                listOfN(reps, char('a')).map { _ -> reps }
            }
        //end::init1[]
    }
}

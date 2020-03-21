package chapter9.solutions.ex5

import chapter9.ParseError
import chapter9.Parser
import chapter9.Parsers

abstract class Listing : Parsers<ParseError> {

    init {

        //tag::init1[]
        fun regex(s: String): Parser<String> = TODO()

        val parser: Parser<Int> = regex("""\d""")
            .flatMap { digit: String ->
                val reps = digit.toInt()
                listOfN(reps, char('a')).map { _ ->
                    reps
                }
            }
        //end::init1[]
    }
}

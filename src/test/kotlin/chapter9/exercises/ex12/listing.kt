package chapter9.exercises.ex12

import arrow.core.Option
import chapter9.solutions.final.Location
import chapter9.solutions.final.ParseError
import chapter9.solutions.final.Parser
import chapter9.solutions.final.ParserDsl
import chapter9.solutions.final.State
import utils.SOLUTION_HERE

abstract class Solution : ParserDsl<ParseError>() {

    private fun firstNonMatchingIndex(
        input: String,
        start: String,
        offset: Int
    ): Option<Int> = TODO()

    private fun State.advanceBy(i: Int): Location = TODO()

    //tag::init1[]
    override fun string(s: String): Parser<String> =

        SOLUTION_HERE()
    //end::init1[]
}

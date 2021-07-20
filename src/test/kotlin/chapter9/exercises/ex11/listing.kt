package chapter9.exercises.ex11

import arrow.core.Option
import chapter9.solutions.ex11.State
import chapter9.solutions.final.Location
import chapter9.solutions.final.ParseError
import chapter9.solutions.final.Parser
import chapter9.solutions.final.ParserDsl
import utils.SOLUTION_HERE

typealias State = Location

//tag::init1[]
abstract class Parser : ParserDsl<ParseError>() {

    override fun string(s: String): Parser<String> =

        SOLUTION_HERE()

    private fun firstNonMatchingIndex(
        s1: String,
        s2: String,
        offset: Int
    ): Option<Int> =

        SOLUTION_HERE()

    private fun State.advanceBy(i: Int): State =

        SOLUTION_HERE()

    override fun regex(r: String): Parser<String> =

        SOLUTION_HERE()

    private fun String.findPrefixOf(r: Regex): Option<MatchResult> =

        SOLUTION_HERE()

    override fun <A> succeed(a: A): Parser<A> =

        SOLUTION_HERE()

    override fun <A> slice(p: Parser<A>): Parser<String> =

        SOLUTION_HERE()

    private fun State.slice(n: Int): String =

        SOLUTION_HERE()
}
//end::init1[]

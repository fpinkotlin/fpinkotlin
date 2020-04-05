package chapter9.exercises.ex11

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import arrow.core.toOption
import chapter9.solutions.ex11.State
import chapter9.solutions.final.Failure
import chapter9.solutions.final.Location
import chapter9.solutions.final.ParseError
import chapter9.solutions.final.Parser
import chapter9.solutions.final.ParserDsl
import chapter9.solutions.final.Success

typealias State = Location

abstract class MyParser : ParserDsl<ParseError>() {
    //tag::init1[]
    override fun string(s: String): Parser<String> = TODO()

    private fun firstNonMatchingIndex(
        s1: String,
        s2: String,
        offset: Int
    ): Option<Int> = TODO()

    private fun State.advanceBy(i: Int): State = TODO()

    override fun regex(r: String): Parser<String> = TODO()

    private fun String.findPrefixOf(r: Regex): Option<MatchResult> = TODO()

    override fun <A> succeed(a: A): Parser<A> = TODO()

    override fun <A> slice(p: Parser<A>): Parser<String> = TODO()

    private fun State.slice(n: Int): String = TODO()
    //end::init1[]
}

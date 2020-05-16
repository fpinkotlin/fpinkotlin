package chapter9.solutions.ex12

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import chapter9.solutions.final.Failure
import chapter9.solutions.final.Location
import chapter9.solutions.final.ParseError
import chapter9.solutions.final.Parser
import chapter9.solutions.final.ParserDsl
import chapter9.solutions.final.State
import chapter9.solutions.final.Success

abstract class Solution : ParserDsl<ParseError>() {

    private fun firstNonMatchingIndex(
        input: String,
        start: String,
        offset: Int
    ): Option<Int> = TODO()

    private fun State.advanceBy(i: Int): Location = TODO()

    //tag::init1[]
    override fun string(s: String): Parser<String> =
        { state: State ->
            when (val idx =
                firstNonMatchingIndex(state.input, s, state.offset)) {
                is None ->
                    Success(s, s.length)
                is Some ->
                    Failure(
                        state.advanceBy(idx.t).toError("'$s'"),
                        idx.t != 0
                    )
            }
        }
    //end::init1[]
}

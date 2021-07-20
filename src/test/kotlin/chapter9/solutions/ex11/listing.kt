package chapter9.solutions.ex11

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import arrow.core.toOption
import chapter9.solutions.final.Failure
import chapter9.solutions.final.Location
import chapter9.solutions.final.ParseError
import chapter9.solutions.final.Parser
import chapter9.solutions.final.ParserDsl
import chapter9.solutions.final.Success

typealias State = Location

//tag::init1[]
abstract class Parser : ParserDsl<ParseError>() {
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

    private fun firstNonMatchingIndex(
        s1: String,
        s2: String,
        offset: Int
    ): Option<Int> {
        var i = 0
        while (i < s1.length && i < s2.length) {
            if (s1[i + offset] != s2[i])
                return Some(i)
            else i += 1
        }
        return if (s1.length - offset >= s2.length) None
        else Some(s1.length - offset)
    }

    private fun State.advanceBy(i: Int) =
        this.copy(offset = this.offset + i)

    override fun regex(r: String): Parser<String> =
        { state: State ->
            when (val prefix = state.input.findPrefixOf(r.toRegex())) {
                is Some ->
                    Success(prefix.t.value, prefix.t.value.length)
                is None ->
                    Failure(state.toError("regex ${r.toRegex()}"))
            }
        }

    private fun String.findPrefixOf(r: Regex): Option<MatchResult> =
        r.find(this).toOption().filter { it.range.first == 0 }

    override fun <A> succeed(a: A): Parser<A> = { Success(a, 0) }

    override fun <A> slice(p: Parser<A>): Parser<String> =
        { state: State ->
            when (val result = p(state)) {
                is Success ->
                    Success(state.slice(result.consumed), result.consumed)
                is Failure -> result
            }
        }

    private fun State.slice(n: Int) =
        this.input.substring(this.offset..this.offset + n)
}
//end::init1[]

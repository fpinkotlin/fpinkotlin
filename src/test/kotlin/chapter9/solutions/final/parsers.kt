package chapter9.solutions.final

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import arrow.core.getOrElse
import arrow.core.lastOrNone
import arrow.core.toOption

data class ParseError(
    val stack: List<Pair<Location, String>> = emptyList()
) {

    fun push(loc: Location, msg: String): ParseError =
        this.copy(stack = listOf(loc to msg) + stack)

    fun label(s: String): ParseError =
        ParseError(latestLoc()
            .map { it to s }
            .toList())

    private fun latest(): Option<Pair<Location, String>> =
        stack.lastOrNone()

    private fun latestLoc(): Option<Location> = latest().map { it.first }

    /*
     * Display collapsed error stack - any adjacent stack elements with the
     * same location are combined on one line. For the bottommost error, we
     * display the full line, with a caret pointing to the column of the
     * error.
     * Example:
     * 1.1 file 'companies.json'; array
     * 5.1 object
     * 5.2 key-value
     * 5.10 ':'
     * { "MSFT" ; 24,
     *          ^
     */
    override fun toString(): String =
        if (stack.isEmpty()) "no error message"
        else {
            val collapsed = collapseStack(stack)
            val context =
                collapsed.lastOrNone()
                    .map { "\n\n" + it.first.line }
                    .getOrElse { "" } +
                    collapsed.lastOrNone()
                        .map { "\n" + it.first.col }
                        .getOrElse { "" }

            collapsed.joinToString { (loc, msg) ->
                "${loc.line}.${loc.col} $msg"
            } + context
        }

    /* Builds a collapsed version of the given error stack -
     * messages at the same location have their messages merged,
     * separated by semicolons.
     */
    private fun collapseStack(
        stk: List<Pair<Location, String>>
    ): List<Pair<Location, String>> =
        stk.groupBy { it.first }
            .mapValues { it.value.joinToString() }
            .toList()
            .sortedBy { it.first.offset }
}

typealias State = Location

data class Location(val input: String, val offset: Int = 0) {

    private val slice by lazy { input.slice(0..offset + 1) }

    val line by lazy {
        slice.count { it == '\n' } + 1
    }

    val col by lazy {
        when (val n = slice.lastIndexOf('\n')) {
            -1 -> offset + 1
            else -> offset - n
        }
    }

    fun toError(msg: String) = ParseError(listOf(this to msg))
}

typealias Parser<A> = (Location) -> Result<A>

sealed class Result<out A>
data class Success<out A>(val a: A, val consumed: Int) : Result<A>()
data class Failure(
    val get: ParseError,
    val isCommitted: Boolean = false
) : Result<Nothing>()

/*
 * Wish we could make this an interface, but Kotlin prevents us from
 * implementing the ParserDsl with extension methods that have the same
 * name as these combinators.
 *
 * For instance, the following two methods conflict with each other:
 *
 *      fun <A> or(p1: Parser<out A>, p2: Parser<out A>): Parser<A>
 *      infix fun <A> Parser<out A>.or(p: Parser<out A>): Parser<A>
 */
abstract class Parsers<PE> {

    // primitives

    internal abstract fun string(s: String): Parser<String>

    internal abstract fun regex(r: String): Parser<String>

    internal abstract fun <A> slice(p: Parser<A>): Parser<String>

    internal abstract fun <A> tag(msg: String, pa: Parser<A>): Parser<A>

    internal abstract fun <A> scope(msg: String, pa: Parser<A>): Parser<A>

    internal abstract fun <A, B> flatMap(
        p1: Parser<A>,
        f: (A) -> Parser<B>
    ): Parser<B>

    internal abstract fun <A> attempt(p: Parser<A>): Parser<A>

    internal abstract fun <A> or(
        pa: Parser<out A>,
        pb: () -> Parser<out A>
    ): Parser<A>

    // other combinators

    internal abstract fun char(c: Char): Parser<Char>

    internal abstract fun <A> succeed(a: A): Parser<A>

    internal abstract fun <A> many(p: Parser<A>): Parser<List<A>>

    internal abstract fun <A> many1(p: Parser<A>): Parser<List<A>>

    internal abstract fun <A> listOfN(
        n: Int,
        p: Parser<A>
    ): Parser<List<A>>

    internal abstract fun <A, B> product(
        pa: Parser<A>,
        pb: () -> Parser<B>
    ): Parser<Pair<A, B>>

    internal abstract fun <A, B, C> map2(
        pa: Parser<A>,
        pb: () -> Parser<B>,
        f: (A, B) -> C
    ): Parser<C>

    internal abstract fun <A, B> map(pa: Parser<A>, f: (A) -> B): Parser<B>

    internal abstract fun <A> defer(pa: Parser<A>): () -> Parser<A>

    internal abstract fun <A> skipR(
        pa: Parser<A>,
        ps: Parser<String>
    ): Parser<A>

    internal abstract fun <B> skipL(
        ps: Parser<String>,
        pb: Parser<B>
    ): Parser<B>

    internal abstract fun <A> surround(
        start: Parser<String>,
        stop: Parser<String>,
        p: Parser<A>
    ): Parser<A>

    internal abstract fun <A> sep(
        p1: Parser<A>,
        p2: Parser<String>
    ): Parser<List<A>>

    internal abstract fun <A> run(
        p: Parser<A>,
        input: String
    ): Result<A>
}

open class ParsersImpl<PE> : Parsers<PE>() {

    override fun string(s: String): Parser<String> =
        { state: State ->
            when (val idx =
                firstNonMatchingIndex(state.input, s, state.offset)) {
                is None ->
                    Success(s, s.length)
                is Some ->
                    Failure(
                        state.advanceBy(idx.t).toError("'$s'")
                            .tag("expected"),
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
            if (s1[i + offset] != s2[i]) return Some(i)
            i += 1
        }
        return if (s1.length - offset >= s2.length) None
        else Some(s1.length - offset)
    }

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

    override fun <A, B> flatMap(
        p1: Parser<A>,
        f: (A) -> Parser<B>
    ): Parser<B> =
        { state ->
            when (val result = p1(state)) {
                is Success ->
                    f(result.a)(state.advanceBy(result.consumed))
                        .addCommit(result.consumed != 0)
                        .advanceSuccess(result.consumed)
                is Failure -> result
            }
        }

    private fun State.advanceBy(i: Int): Location =
        this.copy(offset = this.offset + i)

    private fun <A> Result<A>.addCommit(committed: Boolean): Result<A> =
        when (this) {
            is Failure ->
                Failure(this.get, this.isCommitted || committed)
            is Success -> this
        }

    private fun <A> Result<A>.advanceSuccess(n: Int): Result<A> =
        when (this) {
            is Success ->
                Success(this.a, this.consumed + n)
            is Failure -> this
        }

    override fun <A> attempt(p: Parser<A>): Parser<A> =
        { state -> p(state).uncommit() }

    private fun <A> Result<A>.uncommit(): Result<A> =
        when (this) {
            is Failure ->
                if (this.isCommitted)
                    Failure(this.get, false)
                else this
            is Success -> this
        }

    override fun <A> or(pa: Parser<A>, pb: () -> Parser<A>): Parser<A> =
        { state ->
            when (val result = pa(state)) {
                is Failure ->
                    if (!result.isCommitted) pb()(state)
                    else result
                is Success -> result
            }
        }

    infix fun <T> T.cons(la: List<T>): List<T> = listOf(this) + la

    private fun <A> Result<A>.mapError(
        f: (ParseError) -> ParseError
    ): Result<A> =
        when (this) {
            is Failure -> Failure(f(this.get))
            is Success -> this
        }

    fun ParseError.tag(msg: String): ParseError {

        val latest = this.stack.lastOrNone()

        val latestLocation = latest.map { it.first }

        return ParseError(latestLocation.map { it to msg }.toList())
    }

    override fun <A> tag(msg: String, pa: Parser<A>): Parser<A> =
        { state -> pa(state).mapError { it.tag(msg) } }

    override fun <A> scope(msg: String, pa: Parser<A>): Parser<A> =
        { state -> pa(state).mapError { it.push(state, msg) } }

    override fun <A> many(p: Parser<A>): Parser<List<A>> =
        or(
            map2(p, { many(p) }) { a, la -> a cons la },
            defer(succeed(emptyList()))
        )

    override fun <A> many1(p: Parser<A>): Parser<List<A>> =
        map2(p, { many(p) }) { a: A, b: List<A> -> a cons b }

    override fun char(c: Char): Parser<Char> =
        map(string(c.toString())) { it.first() }

    override fun <A> succeed(a: A): Parser<A> = { Success(a, 0) }

    override fun <A> listOfN(n: Int, p: Parser<A>): Parser<List<A>> =
        if (n > 0)
            map2(p, { listOfN(n - 1, p) }) { a, la -> a cons la }
        else succeed(emptyList())

    override fun <A, B> product(
        pa: Parser<A>,
        pb: () -> Parser<B>
    ): Parser<Pair<A, B>> =
        flatMap(pa, { a -> map(pb()) { b -> a to b } })

    override fun <A, B, C> map2(
        pa: Parser<A>,
        pb: () -> Parser<B>,
        f: (A, B) -> C
    ): Parser<C> =
        flatMap(pa, { a -> map(pb()) { b -> f(a, b) } })

    override fun <A, B> map(pa: Parser<A>, f: (A) -> B): Parser<B> =
        flatMap(pa, { a -> succeed(f(a)) })

    override fun <A> defer(pa: Parser<A>): () -> Parser<A> = { pa }

    override fun <A> skipR(pa: Parser<A>, ps: Parser<String>): Parser<A> =
        map2(pa, defer(slice(ps))) { a, _ -> a }

    override fun <B> skipL(ps: Parser<String>, pb: Parser<B>): Parser<B> =
        map2(slice(ps), defer(pb)) { _, b -> b }

    override fun <A> surround(
        start: Parser<String>,
        stop: Parser<String>,
        p: Parser<A>
    ): Parser<A> =
        skipL(start, (skipR(p, stop)))

    private fun <A> sep1(
        p1: Parser<A>,
        p2: Parser<String>
    ): Parser<List<A>> =
        map2(p1, defer(many(skipL(p2, p1)))) { a, b -> listOf(a) + b }

    override fun <A> sep(
        p1: Parser<A>,
        p2: Parser<String>
    ): Parser<List<A>> =
        or(sep1(p1, p2), defer(succeed(emptyList())))

    override fun <A> run(
        p: Parser<A>,
        input: String
    ): Result<A> = p(Location(input))
}

abstract class ParserDsl<PE> : ParsersImpl<PE>() {

    infix fun <A> Parser<out A>.or(p: Parser<out A>): Parser<A> =
        this@ParserDsl.or(this, p.defer())

    infix fun <A, B> Parser<A>.product(
        pb: () -> Parser<B>
    ): Parser<Pair<A, B>> =
        this@ParserDsl.product(this, pb)

    infix fun <A, B> Parser<A>.flatMap(f: (A) -> Parser<B>): Parser<B> =
        this@ParserDsl.flatMap(this, f)

    infix fun <A, B> Parser<A>.map(f: (A) -> B): Parser<B> =
        this@ParserDsl.map(this, f)

    infix fun <A> Parser<A>.tag(msg: String): Parser<A> =
        this@ParserDsl.tag(msg, this)

    infix fun <A> Parser<A>.scope(msg: String): Parser<A> =
        this@ParserDsl.scope(msg, this)

    fun <A> Parser<A>.many(): Parser<List<A>> =
        this@ParserDsl.many(this)

    fun <A> Parser<A>.slice(): Parser<String> =
        this@ParserDsl.slice(this)

    fun <A> Parser<A>.defer(): () -> Parser<A> =
        this@ParserDsl.defer(this)

    infix fun <A> Parser<A>.skipR(p: Parser<String>): Parser<A> =
        this@ParserDsl.skipR(this, p)

    infix fun <B> Parser<String>.skipL(p: Parser<B>): Parser<B> =
        this@ParserDsl.skipL(this, p)

    infix fun <A> Parser<A>.sep(p: Parser<String>): Parser<List<A>> =
        this@ParserDsl.sep(this, p)
}

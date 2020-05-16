package chapter9.solutions.ex9

import java.util.regex.Pattern

sealed class JSON

object JNull : JSON()
data class JNumber(val get: Double) : JSON()
data class JString(val get: String) : JSON()
data class JBoolean(val get: Boolean) : JSON()
data class JArray(val get: List<JSON>) : JSON()
data class JObject(val get: Map<String, JSON>) : JSON()

object ParseError

interface Parser<A>

//tag::init[]
abstract class Parsers<PE> {

    // primitives

    internal abstract fun string(s: String): Parser<String>

    internal abstract fun regex(r: String): Parser<String>

    internal abstract fun <A> slice(p: Parser<A>): Parser<String>

    internal abstract fun <A> succeed(a: A): Parser<A>

    internal abstract fun <A, B> flatMap(
        p1: Parser<A>,
        f: (A) -> Parser<B>
    ): Parser<B>

    internal abstract fun <A> or(
        p1: Parser<out A>,
        p2: () -> Parser<out A>
    ): Parser<A>

    // other combinators

    internal abstract fun char(c: Char): Parser<Char>

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

    internal abstract fun <A> sep(
        p1: Parser<A>,
        p2: Parser<String>
    ): Parser<List<A>>

    internal abstract fun <A> surround(
        start: Parser<String>,
        stop: Parser<String>,
        p: Parser<A>
    ): Parser<A>
}

abstract class ParsersDsl<PE> : Parsers<PE>() {

    fun <A> Parser<A>.defer(): () -> Parser<A> = defer(this)

    fun <A, B> Parser<A>.map(f: (A) -> B): Parser<B> =
        this@ParsersDsl.map(this, f)

    fun <A> Parser<A>.many(): Parser<List<A>> =
        this@ParsersDsl.many(this)

    infix fun <A> Parser<out A>.or(p: Parser<out A>): Parser<A> =
        this@ParsersDsl.or(this, p.defer())

    infix fun <A, B> Parser<A>.product(p: Parser<B>): Parser<Pair<A, B>> =
        this@ParsersDsl.product(this, p.defer())

    infix fun <A> Parser<A>.sep(p: Parser<String>): Parser<List<A>> =
        this@ParsersDsl.sep(this, p)

    infix fun <A> Parser<A>.skipR(p: Parser<String>): Parser<A> =
        this@ParsersDsl.skipR(this, p)

    infix fun <B> Parser<String>.skipL(p: Parser<B>): Parser<B> =
        this@ParsersDsl.skipL(this, p)

    infix fun <T> T.cons(la: List<T>): List<T> = listOf(this) + la
}

abstract class JSONParsers : ParsersDsl<ParseError>() {

    // {
    //   "Company name" : "Microsoft Corporation",
    //   "Ticker": "MSFT",
    //   "Active": true,
    //   "Price": 30.66,
    //   "Shares outstanding": 8.38e9,
    //   "Related companies": [ "HPQ", "IBM", "YHOO", "DELL", "GOOG" ]
    // }

    val JSON.parser: Parser<JSON>
        get() = succeed(this)

    val String.rp: Parser<String>
        get() = regex(this)

    val String.sp: Parser<String>
        get() = string(this)

    fun thru(s: String): Parser<String> =
        ".*?${Pattern.quote(s)}".rp

    val quoted: Parser<String> =
        "\"".sp skipL thru("\"").map { it.dropLast(1) }

    val doubleString: Parser<String> =
        "[-+]?([0-9]*\\.)?[0-9]+([eE][-+]?[0-9]+)?".rp

    val double: Parser<Double> = doubleString.map { it.toDouble() }

    val lit: Parser<JSON> =
        JNull.parser or
            double.map { JNumber(it) } or
            JBoolean(true).parser or
            JBoolean(false).parser or
            quoted.map { JString(it) }

    val value: Parser<JSON> = lit or obj() or array()

    val keyval: Parser<Pair<String, JSON>> =
        quoted product (":".sp skipL value)

    val whitespace: Parser<String> = """\s*""".rp

    val eof: Parser<String> = """\z""".rp

    fun array(): Parser<JArray> =
        surround("[".sp, "]".sp,
            (value sep ",".sp).map { vs -> JArray(vs) })

    fun obj(): Parser<JObject> =
        surround("{".sp, "}".sp,
            (keyval sep ",".sp).map { kvs -> JObject(kvs.toMap()) })

    fun <A> root(p: Parser<A>): Parser<A> = p skipR eof

    val jsonParser: Parser<JSON> =
        root(whitespace skipL (obj() or array()))
}
//end::init[]

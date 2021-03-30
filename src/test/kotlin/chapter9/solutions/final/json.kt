package chapter9.solutions.final

import java.util.regex.Pattern

sealed class JSON

object JNull : JSON()
data class JNumber(val get: Double) : JSON()
data class JString(val get: String) : JSON()
data class JBoolean(val get: Boolean) : JSON()
data class JArray(val get: List<JSON>) : JSON()
data class JObject(val get: Map<String, JSON>) : JSON()

object JSONParser : ParserDsl<ParseError>() {

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

    fun quoted(): Parser<String> =
        "\"".sp skipL thru("\"").map { it.dropLast(1) }

    val doubleString: Parser<String> =
        "[-+]?([0-9]*\\.)?[0-9]+([eE][-+]?[0-9]+)?".rp

    val double: Parser<Double> = doubleString.map { it.toDouble() }

    val lit: Parser<JSON> =
        JNull.parser or
            double.map { JNumber(it) } or
            JBoolean(true).parser or
            JBoolean(false).parser or
            quoted().map { JString(it) } scope "literal"

    fun value(): Parser<JSON> = lit or obj() or array()

    fun keyval(): Parser<Pair<String, JSON>> =
        quoted() product (":".sp skipL value()).defer()

    fun whitespace(): Parser<String> = """\s*""".rp

    fun eof(): Parser<String> = """\z""".rp

    fun array(): Parser<JArray> =
        surround("[".sp, "]".sp,
            (value() sep ",".sp).map { vs -> JArray(vs) }
        ) scope "array"

    fun obj(): Parser<JObject> =
        surround("{".sp, "}".sp,
            (keyval() sep ",".sp).map { kvs ->
                JObject(kvs.toMap())
            }
        ) scope "object"

    fun <A> root(p: Parser<A>): Parser<A> = p skipR eof()

    val jsonParser: Parser<JSON> by lazy {
        root(whitespace() skipL (obj() or array()))
    }
}

object Example : ParserDsl<ParseError>() {
    val parser by lazy { char('a') product char('b').defer() }
}

fun main() {
    println(Example.run(Example.parser, "a"))
}

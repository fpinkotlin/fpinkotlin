package chapter9.solutions.ex8

import chapter9.ParseError
import chapter9.Parser
import chapter9.Parsers
import chapter9.solutions.ex8.JSON.JArray
import chapter9.solutions.ex8.JSON.JBoolean
import chapter9.solutions.ex8.JSON.JNull
import chapter9.solutions.ex8.JSON.JNumber
import chapter9.solutions.ex8.JSON.JObject
import chapter9.solutions.ex8.JSON.JString
import java.util.regex.Pattern

sealed class JSON {
    object JNull : JSON()
    data class JNumber(val get: Double) : JSON()
    data class JString(val get: String) : JSON()
    data class JBoolean(val get: Boolean) : JSON()
    data class JArray(val get: List<JSON>) : JSON()
    data class JObject(val get: Map<String, JSON>) : JSON()
}

abstract class Listing : Parsers<ParseError> {

    // {
    //   "Company name" : "Microsoft Corporation",
    //   "Ticker": "MSFT",
    //   "Active": true,
    //   "Price": 30.66,
    //   "Shares outstanding": 8.38e9,
    //   "Related companies": [ "HPQ", "IBM", "YHOO", "DELL", "GOOG" ]
    // }

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

    fun <A> root(p: Parser<A>): Parser<A> = p skipR eof

    fun <A> surround(
        start: Parser<String>,
        stop: Parser<String>,
        p: Parser<A>
    ) = start skipL (p skipR stop)

    fun array(): Parser<JArray> =
        surround("[".sp, "]".sp,
            (value sep ",".sp).map { vs -> JArray(vs) })

    fun obj(): Parser<JObject> =
        surround("{".sp, "}".sp,
            (keyval sep ",".sp).map { kvs -> JObject(kvs.toMap()) })

    val jsonParser: Parser<JSON> =
        root(whitespace skipL (obj() or array()))
}

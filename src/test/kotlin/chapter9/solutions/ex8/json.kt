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

    fun <A> skipR(p1: Parser<A>, p2: Parser<String>): Parser<A> =
        map2(p1, p2.slice()) { a, _ -> a }

    fun <B> skipL(p1: Parser<String>, p2: Parser<B>): Parser<B> =
        map2(p1.slice(), p2) { _, b -> b }

    val whitespace: Parser<String> = regex("""\s*""")

    val eof: Parser<String> = regex("""\z""")

    fun <A> root(p: Parser<A>): Parser<A> = skipR(p, eof)

    fun <A> surround(
        start: Parser<String>,
        stop: Parser<String>,
        p: Parser<A>
    ) = skipL(start, skipR(p, stop))

    fun thru(s: String): Parser<String> = regex(".*?" + Pattern.quote(s))

    val quoted: Parser<String> =
        skipL(string("\""), thru("\"").map { it.dropLast(1) })

    val doubleString: Parser<String> =
        regex("[-+]?([0-9]*\\.)?[0-9]+([eE][-+]?[0-9]+)?")

    val double: Parser<Double> = doubleString.map { it.toDouble() }

    private fun JSON.parser(): Parser<JSON> = Parser(this)

    val lit: Parser<JSON> =
        or(
            or(
                or(
                    or(
                        JNull.parser(),
                        double.map { JNumber(it) }
                    ),
                    JBoolean(true).parser()
                ),
                JBoolean(false).parser()
            ),
            quoted.map { JString(it) }
        )

    fun <A> sep1(p: Parser<A>, p2: Parser<String>): Parser<List<A>> =
        map2(p, skipL(p2, p).many()) { a, b -> a cons b }

    fun <A> sep(p1: Parser<A>, p2: Parser<String>): Parser<List<A>> =
        or(sep1(p1, p2), succeed(emptyList()))

    val value: Parser<JSON> = or(or(lit, obj()), array())

    val keyval: Parser<Pair<String, JSON>> =
        quoted product skipL(string(":"), value)

    fun array(): Parser<JArray> = surround(string("["), string("]"),
        sep(value, string(",")).map { vs -> JArray(vs) })

    fun obj(): Parser<JObject> = surround(string("{"), string("}"),
        sep(keyval, string(",")).map { kvs -> JObject(kvs.toMap()) })

    fun <PE> jsonParser(parsers: Parsers<PE>): Parser<JSON> =
        root(skipL(whitespace, or(obj(), array())))

}
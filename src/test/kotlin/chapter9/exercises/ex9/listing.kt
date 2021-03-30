package chapter9.exercises.ex9

import utils.SOLUTION_HERE

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

    // other combinators here
}

abstract class ParsersDsl<PE> : Parsers<PE>() {
    // syntactic sugar here
}

abstract class JSONParsers : ParsersDsl<ParseError>() {
    val jsonParser: Parser<JSON> =

        SOLUTION_HERE()
}
//end::init[]

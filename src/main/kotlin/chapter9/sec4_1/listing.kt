package chapter9.sec4_1

import chapter9.sec6_4.Parser

object ParseError

interface Parsers<PE>

abstract class ParsersImpl<PE> : Parsers<PE>
//tag::init1[]
object JSONParser : ParsersImpl<ParseError>() { // <1>
    val jsonParser: Parser<JSON> = TODO() // <2>
}
//end::init1[]

//tag::init2[]
sealed class JSON {
    object JNull : JSON()
    data class JNumber(val get: Double) : JSON()
    data class JString(val get: String) : JSON()
    data class JBoolean(val get: Boolean) : JSON()
    data class JArray(val get: List<JSON>) : JSON()
    data class JObject(val get: Map<String, JSON>) : JSON()
}
//end::init2[]

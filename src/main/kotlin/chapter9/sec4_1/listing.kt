package chapter9.sec4_1

import chapter9.sec2_1.Parser
import chapter9.sec2_1.Parsers

//tag::init1[]
fun <PE> jsonParser( // <1>
    parsers: Parsers<PE> // <2>
): Parser<JSON> = TODO() // <3>
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
//tag::init2[]

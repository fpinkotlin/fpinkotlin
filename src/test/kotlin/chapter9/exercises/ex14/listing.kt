package chapter9.exercises.ex14

import arrow.core.Option
import chapter9.solutions.final.Location

//tag::init[]
data class ParseError(val stack: List<Pair<Location, String>> = emptyList()) {

    fun push(loc: Location, msg: String): ParseError = TODO()

    fun label(s: String): ParseError = TODO()

    fun latest(): Option<Pair<Location, String>> = TODO()

    fun latestLoc(): Option<Location> = TODO()
}
//end::init[]
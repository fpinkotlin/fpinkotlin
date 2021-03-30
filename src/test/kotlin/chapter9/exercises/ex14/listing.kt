package chapter9.exercises.ex14

import arrow.core.Option
import chapter9.solutions.final.Location
import utils.SOLUTION_HERE

//tag::init[]
data class ParseError(val stack: List<Pair<Location, String>> = emptyList()) {

    fun push(loc: Location, msg: String): ParseError =

        SOLUTION_HERE()

    fun label(s: String): ParseError =

        SOLUTION_HERE()

    fun latest(): Option<Pair<Location, String>> =

        SOLUTION_HERE()

    fun latestLoc(): Option<Location> =

        SOLUTION_HERE()
}
//end::init[]
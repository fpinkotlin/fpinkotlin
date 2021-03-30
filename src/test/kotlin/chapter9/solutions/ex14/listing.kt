package chapter9.solutions.ex14

import arrow.core.Option
import arrow.core.getOrElse
import arrow.core.lastOrNone
import chapter9.solutions.final.Location

//tag::init[]
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
//end::init[]

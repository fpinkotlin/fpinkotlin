package chapter9.sec6_0

interface Location

//tag::init1[]
interface Parser<A> // <1>

data class ParseError(val stack: List<Pair<Location, String>>)

abstract class Parsers<PE> { // <2>
    internal abstract fun <A> or(p1: Parser<A>, p2: Parser<A>): Parser<A>
}

open class ParsersImpl<PE>() : Parsers<PE>() { // <3>
    override fun <A> or(p1: Parser<A>, p2: Parser<A>): Parser<A> = TODO()
}

abstract class ParserDsl<PE> : ParsersImpl<PE>() { // <4>
    infix fun <A> Parser<A>.or(p: Parser<A>): Parser<A> =
        this@ParserDsl.or(this, p) // <5>
}

object Example : ParserDsl<ParseError>() { // <6>
    init {
        val p1: Parser<String> = TODO()
        val p2: Parser<String> = TODO()
        val p3 = p1 or p2
    }
}
//end::init1[]

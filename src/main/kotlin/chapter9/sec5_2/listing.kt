package chapter9.sec5_2

import arrow.core.Either
import chapter9.Parser
import chapter9.sec5_1.Listing.Location

interface Parsers {

    fun string(s: String): Parser<String>

    fun <A> Parser<A>.many(): Parser<List<A>>

    infix fun <A, B> Parser<A>.product(pb: Parser<B>): Parser<Pair<A, B>>

    //tag::init4[]
    fun <A> run(p: Parser<A>, input: String): Either<ParseError, A>
    //end::init4[]
}

interface Listing : Parsers {

    fun <A> tag(msg: String, p: Parser<A>): Parser<A>

    fun listing() {
        //tag::init1[]
        tag("first magic word", string("abra")) product // <1>
            string(" ").many() product // <2>
            tag("second magic word", string("cadabra")) // <3>
        //end::init1[]
    }

    //tag::init2[]
    fun <A> scope(msg: String, p: Parser<A>): Parser<A>
    //end::init2[]
}

//tag::init3[]
data class ParseError(val stack: List<Pair<Location, String>>)
//end::init3[]

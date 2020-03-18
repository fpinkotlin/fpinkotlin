package chapter9.sec1_1

import arrow.core.Either
import arrow.core.Right

//tag::init3[]
class Parser<A> // <1>

interface Parsers<PE> { // <2>

    //tag::init1[]
    fun char(c: Char): Parser<Char>
    //end::init1[]

    //tag::init2[]
    fun <A> run(p: Parser<A>, input: String): Either<PE, A>
    //end::init2[]

    //tag::stringparser[]
    fun string(s: String): Parser<String>

    //end::stringparser[]
    //tag::orstringparser[]
    fun orString(s1: String, s2: String): Parser<String>

    //end::orstringparser[]
    //tag::orparser[]
    fun <A> or(a1: Parser<A>, a2: Parser<A>): Parser<A>
    //end::orparser[]
}
//end::init3[]

object ParseError

abstract class Example : Parsers<ParseError> {
    init {
        val c = 'a'
        //tag::init4[]
        run(char(c), c.toString()) == Right(c)
        //end::init4[]

        val s = "abracadabra"
        //tag::init5[]
        run(string(s), s) == Right(s)
        //end::init5[]

        //tag::init6[]
        run(or(string("abra"), string("cadabra")), "abra") ==
            Right("abra")
        run(or(string("abra"), string("cadabra")), "cadabra") ==
            Right("abra")
        //end::init6[]
    }
}

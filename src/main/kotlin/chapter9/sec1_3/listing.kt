package chapter9.sec1_3

import arrow.core.Either
import arrow.core.Right

class Parser<A>

interface Parsers<PE> {

    fun <A> run(p: Parser<A>, input: String): Either<PE, A>

    //tag::init1[]
    fun <A> listOfN(n: Int, p: Parser<A>): Parser<List<A>>
    //end::init1[]

    infix fun String.or(other: String): Parser<String>
}

object ParseError

abstract class Example : Parsers<ParseError> {
    init {
        //tag::init2[]
        run(listOfN(3, "ab" or "cad"), "ababcad") == Right("ababcad")
        run(listOfN(3, "ab" or "cad"), "cadabab") == Right("cadabab")
        run(listOfN(3, "ab" or "cad"), "ababab") == Right("ababab")
        //end::init2[]
    }
}

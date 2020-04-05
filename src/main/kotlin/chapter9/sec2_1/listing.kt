package chapter9.sec2_1

import arrow.core.Either

interface Parsers<PE> {

    interface Parser<A>

    fun char(c: Char): Parser<Char>

    fun string(s: String): Parser<String>

    //tag::init1[]
    fun <A> many(pa: Parser<A>): Parser<List<A>>
    //end::init1[]

    //tag::init2[]
    fun <A, B> map(pa: Parser<A>, f: (A) -> B): Parser<B>
    //end::init2[]

    fun <A> run(p: Parser<A>, input: String): Either<PE, A>
}

object ParseError

abstract class Example : Parsers<ParseError> {
    init {
        //tag::init3[]
        map(many(char('a'))) { it.size }
        //end::init3[]

        val p = string("aaa")
        //tag::init4[]
        map(p) { a -> a } == p
        //end::init4[]
    }
}

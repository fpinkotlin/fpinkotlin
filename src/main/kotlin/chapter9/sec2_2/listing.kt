package chapter9.sec2_2

import arrow.core.Either
import arrow.core.Right
import chapter8.sec3.listing3.Gen
import chapter8.sec3.listing3.Prop
import chapter8.sec3.listing3.Prop.Companion.forAll

class Parser<A>(a: A)

interface Parsers<PE> {

    fun string(s: String): Parser<String>

    //tag::init1[]
    fun <A> Parser<A>.many(): Parser<List<A>>

    fun <A, B> Parser<A>.map(f: (A) -> B): Parser<B>
    //end::init1[]

    //tag::init4[]
    fun char(c: Char): Parser<Char> = string(c.toString()).map { it[0] }
    //end::init4[]

    //tag::init5[]
    fun <A> succeed(a: A): Parser<A> = string("").map { a }
    //end::init5[]

    fun <A> run(p: Parser<A>, input: String): Either<PE, A>
}

//tag::init3[]
object ParseError // <1>

abstract class Laws : Parsers<ParseError> { // <2>
    private fun <A> equal( // <3>
        p1: Parser<A>,
        p2: Parser<A>,
        i: Gen<String>
    ): Prop =
        forAll(i) { s -> run(p1, s) == run(p2, s) }

    fun <A> mapLaw(p: Parser<A>, i: Gen<String>): Prop = // <4>
        equal(p, p.map { a -> a }, i)
}
//end::init3[]

abstract class Example : Parsers<ParseError> {
    init {
        //tag::init2[]
        val numA: Parser<Int> = char('a').many().map { it.size }

        run(numA, "aaa") == Right(3)
        run(numA, "b") == Right(0)
        //end::init2[]

        val a = "a"
        val s = "a"
        //tag::init6[]
        run(succeed(a), s) == Right(a)
        //end::init6[]
    }
}

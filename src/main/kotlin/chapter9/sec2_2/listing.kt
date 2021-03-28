package chapter9.sec2_2

import arrow.core.Either
import arrow.core.Right
import chapter8.Gen
import chapter8.Prop
import chapter8.Prop.Companion.forAll

interface Parser<A>

interface Parsers<PE> {

    fun string(s: String): Parser<String>

    fun <A> or(a1: Parser<A>, a2: Parser<A>): Parser<A>

    infix fun Char.or(other: Char): Parser<Char> =
        or(char(this), char(other))

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

    //tag::init7[]
    fun <A> slice(pa: Parser<A>): Parser<String>
    //end::init7[]

    //tag::init10[]
    fun <A> many1(p: Parser<A>): Parser<List<A>>
    //end::init10[]

    //tag::init11[]
    fun <A, B> product(pa: Parser<A>, pb: Parser<B>): Parser<Pair<A, B>>
    //end::init11[]

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
    val listing1 = {
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

        //tag::init8[]
        run(slice(('a' or 'b').many()), "aaba") == Right("aaba")
        //end::init8[]
    }

    val listing2 = {
        fun <A> Parser<A>.slice(): Parser<String> = TODO()
        //tag::init9[]
        char('a').many().slice().map { it.length }
        //end::init9[]

        //tag::init12[]
        infix fun <A, B> Parser<A>.product(
            pb: Parser<B>
        ): Parser<Pair<A, B>>
        //end::init12[]
            = TODO()

        fun <A> Parser<A>.many1(): Parser<List<A>> = TODO()

        //tag::init13[]
        char('a').many().slice().map { it.length } product
            char('b').many1().slice().map { it.length }
        //end::init13[]
    }
}

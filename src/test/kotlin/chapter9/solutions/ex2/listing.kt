package chapter9.solutions.ex2

import chapter9.solutions.final.ParseError
import chapter9.solutions.final.Parser
import chapter9.solutions.final.ParserDsl

abstract class Listing : ParserDsl<ParseError>() {

    private infix fun <A, B> Parser<A>.product(
        pb: Parser<B>
    ): Parser<Pair<A, B>> = TODO()

    val a = succeed("a")
    val b = succeed("b")
    val c = succeed("c")

    val listing1 = {
        //tag::init1[]
        (a product b) product c
        a product (b product c)
        //end::init1[]
    }

    //tag::init2[]
    fun <A, B, C> unbiasL(p: Pair<Pair<A, B>, C>): Triple<A, B, C> =
        Triple(p.first.first, p.first.second, p.second)

    fun <A, B, C> unbiasR(p: Pair<A, Pair<B, C>>): Triple<A, B, C> =
        Triple(p.first, p.second.first, p.second.second)
    //end::init2[]

    val listing2 = {
        //tag::init3[]
        ((a product b) product c).map(::unbiasL) ==
            (a product (b product c)).map(::unbiasR)
        //end::init3[]
    }

    fun f(s: String) = s.length
    fun g(s: String) = s.length

    val listing3 = {
        //tag::init4[]
        a.map(::f) product b.map(::g) ==
            (a product b).map { (a1, b1) -> f(a1) to g(b1) }
        //end::init4[]
    }
}

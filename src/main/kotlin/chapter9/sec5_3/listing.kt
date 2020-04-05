package chapter9.sec5_3

import chapter9.Parser

interface Parsers {

    fun <A, B> Parser<A>.flatMap(f: (A) -> Parser<B>): Parser<B>

    fun string(s: String): Parser<String>

    fun <A> Parser<A>.many(): Parser<List<A>>

    infix fun <A, B> Parser<A>.product(pb: Parser<B>): Parser<Pair<A, B>>

    infix fun <A> Parser<out A>.or(p: Parser<out A>): Parser<A>

    fun <A> scope(msg: String, p: () -> Parser<A>): Parser<A>

    fun <A> tag(msg: String, p: Parser<A>): Parser<A>
}

interface Listing : Parsers {

    fun listing() {
        //tag::init1[]
        val spaces = string(" ").many()

        val p1 = scope("magic spell") {
            string("abra") product spaces product string("cadabra")
        }
        val p2 = scope("gibberish") {
            string("abba") product spaces product string("babba")
        }

        val p = p1 or p2
        //end::init1[]
    }

    //tag::init2[]
    fun <A> attempt(p: Parser<A>): Parser<A>
    //end::init2[]

    fun listint2() {
        val p1 = string("a")
        val p2 = string("b")
        val fail = string("fail")

        //tag::init3[]
        attempt(p1.flatMap { _ -> fail }) or p2 == p2
        //end::init3[]
    }

    fun listing3() {
        val spaces = string(" ").many()

        //tag::init4[]
        (attempt(
            string("abra") product spaces product string("abra")
        ) product string("cadabra")) or
            (string("abra") product spaces product string("cadabra!"))
        //end::init4[]
    }
}

package chapter9

import arrow.core.Either

class ParseError

class Parser<A>(val a: A)

interface Parsers<PE> {

    //primitives

    fun string(s: String): Parser<String>

    fun regex(r: String): Parser<String>

    fun <A> slice(p: Parser<A>): Parser<String>

    fun <A> tag(msg: String, pa: Parser<A>): Parser<A>

    fun <A> scope(msg: String, pa: Parser<A>): Parser<A>

    fun <A, B> Parser<A>.flatMap(f: (A) -> Parser<B>): Parser<B>

    fun <A> attempt(p: Parser<A>): Parser<A>

    infix fun <A> Parser<out A>.or(p: Parser<out A>): Parser<A>

    //other combinators

    fun char(c: Char): Parser<Char>

    fun <A> Parser<A>.many(): Parser<List<A>>

    fun <A> many1(p: Parser<A>): Parser<List<A>>

    fun <A> listOfN(n: Int, p: Parser<A>): Parser<List<A>>

    fun <A, B> Parser<A>.map(f: (A) -> B): Parser<B>

    infix fun <A, B> Parser<A>.product(pb: Parser<B>): Parser<Pair<A, B>>

    fun <A> succeed(a: A): Parser<A>

    fun <A> run(p: Parser<A>, input: String): Either<PE, A>

    fun <A, B, C> map2(
        pa: Parser<A>,
        pb: () -> Parser<B>,
        f: (A, B) -> C
    ): Parser<C>

    infix fun <T> T.cons(la: List<T>): List<T> = listOf(this) + la

    infix fun <A> Parser<A>.skipR(p: Parser<String>): Parser<A> =
        map2(this, { slice(p) }) { a, _ -> a }

    infix fun <B> Parser<String>.skipL(p: Parser<B>): Parser<B> =
        map2(slice(this), { p }) { _, b -> b }

    private fun <A> sep1(
        p: Parser<A>,
        p2: Parser<String>
    ): Parser<List<A>> =
        map2(p, { (p2 skipL p).many() }) { a, b -> a cons b }

    infix fun <A> Parser<A>.sep(p: Parser<String>): Parser<List<A>> =
        sep1(this, p) or succeed(emptyList())

    //sp for string parser
    val String.sp: Parser<String>
        get() = Parser(this)

    //rp for regex parser
    val String.rp: Parser<String>
        get() = regex(this)
}

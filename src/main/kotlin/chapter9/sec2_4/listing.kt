package chapter9.sec2_4

interface Parser<A>

object ParseError

interface Parsers<PE> {
    fun <A, B> Parser<A>.map(f: (A) -> B): Parser<B>
    infix fun <T> T.cons(la: List<T>): List<T>
    infix fun <A> Parser<A>.or(that: Parser<A>): Parser<A>
    fun <A> succeed(a: A): Parser<A>
    fun <A> Parser<A>.defer(): () -> Parser<A>
}

abstract class Listing : Parsers<ParseError> {
    //tag::init1[]
    fun <A, B> product(
        pa: Parser<A>,
        pb: () -> Parser<B>
    ): Parser<Pair<A, B>> = TODO()

    fun <A, B, C> map2(
        pa: Parser<A>,
        pb: () -> Parser<B>,
        f: (A, B) -> C
    ): Parser<C> =
        product(pa, pb).map { (a, b) -> f(a, b) }
    //end::init1[]

    //tag::init2[]
    fun <A> many(pa: Parser<A>): Parser<List<A>> =
        map2(pa, many(pa).defer()) { a, la -> // <1>
            a cons la
        } or succeed(emptyList())
    //end::init2[]

    //tag::init3[]
    fun <A> or(pa: Parser<A>, pb: () -> Parser<A>): Parser<A>
    //end::init3[]
        = TODO()
}

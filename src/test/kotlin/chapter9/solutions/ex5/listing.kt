package chapter9.solutions.ex5

interface Parser<A>

class ParserImpl<A>() : Parser<A>

fun <A, B, C> map2(
    pa: Parser<A>,
    pb: Parser<B>,
    f: (A, B) -> C
): Parser<C> = TODO()

infix fun <A> Parser<out A>.or(p: Parser<out A>): Parser<A> = ParserImpl()

fun <A> succeed(a: A): Parser<A> = ParserImpl()

//tag::init1[]
fun <A> defer(pa: () -> Parser<A>): Parser<A> = pa()
//end::init1[]

//tag::init2[]
fun <A> many(pa: Parser<A>): Parser<List<A>> =
    map2(pa, defer { many(pa) }) { a, la ->
        listOf(a) + la
    } or succeed(emptyList())
//end::init2[]

fun main() {
    // This results in StackOverflowError
    many(succeed("a"))
}
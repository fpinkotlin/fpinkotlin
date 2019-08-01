package chapter3

// tag::exercise3.1[]
fun <A> tail(xs: List<A>): List<A> = when (xs) {
    is Cons -> xs.tail
    is Nil -> throw IllegalStateException("Nil cannot have a `tail`")
}
// end::exercise3.1[]

// tag::exercise3.2[]
fun <A> setHead(xs: List<A>, x: A): List<A> = when (xs) {
    is Cons -> Cons(x, xs.tail)
    is Nil -> throw IllegalStateException("Cannot replace `head` of a Nil list")
}
// end::exercise3.2[]

// tag::exercise3.3[]
fun <A> drop(l: List<A>, n: Int): List<A> =
        if (n == 0) l
        else when (l) {
            is Cons -> drop(l.tail, n - 1)
            is Nil -> throw IllegalStateException("Cannot drop more elements than in list")
        }
// end::exercise3.3[]

// tag::exercise3.4[]
fun <A> dropWhile(l: List<A>, f: (A) -> Boolean): List<A> =
        when (l) {
            is Cons -> if (f(l.head)) dropWhile(l.tail, f) else l
            is Nil -> l
        }
// end::exercise3.4[]

// tag::exercise3.5[]
fun <A> init(l: List<A>): List<A> =
        when (l) {
            is Cons ->
                if (l.tail == Nil) Nil
                else Cons(l.head, init(l.tail))
            is Nil ->
                throw IllegalStateException("Cannot init Nil list")
        }
// end::exercise3.5[]

//Exercise 3.17
fun <A, B> map(ss: List<A>, f: (A) -> B): List<B> = TODO()

//Exercise 3.18
fun <A> filter(ls: List<A>, f: (A) -> Boolean): List<A> = TODO()

//Exercise 3.19
fun <A, B> flatMap(ss: List<A>, f: (A) -> List<B>): List<B> = TODO()


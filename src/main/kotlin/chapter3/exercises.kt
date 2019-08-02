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

// tag::exercise3.6[]
fun <A, B> foldRight(xs: List<A>, z: B, f: (A, B) -> B): B =
        when (xs) {
            is Nil -> z
            is Cons -> f(xs.head, foldRight(xs.tail, z, f))
        }

val f = { x: Int, y: List<Int> -> Cons(x, y) }
val z = Nil as List<Int>

val step1 = foldRight(List.of(1, 2, 3), z, f)
val step2 = Cons(1, foldRight(List.of(2, 3), z, f))
val step3 = Cons(1, Cons(2, foldRight(List.of(3), z, f)))
val step4 = Cons(1, Cons(2, Cons(3, foldRight(List.empty(), z, f))))
val step5: List<Int> = Cons(1, Cons(2, Cons(3, Nil)))
// end::exercise3.6[]

// tag::exercise3.7[]
fun <A> length(xs: List<A>): Int = foldRight(xs, 0, { _, acc -> 1 + acc })
// end::exercise3.7[]

// tag::exercise3.8[]
tailrec fun <A, B> foldLeft(xs: List<A>, z: B, f: (B, A) -> B): B =
        when (xs) {
            is Nil -> z
            is Cons -> foldLeft(xs.tail, f(z, xs.head), f)
        }
// end::exercise3.8[]

// tag::exercise3.9[]
fun sumL(xs: List<Int>): Int = foldLeft(xs, 0, { x, y -> x + y })

fun productL(xs: List<Double>): Double = foldLeft(xs, 1.0, { x, y -> x * y })

fun <A> lengthL(xs: List<A>): Int = foldLeft(xs, 0, { acc, _ -> acc + 1 })
// end::exercise3.9[]


//Exercise 3.17
fun <A, B> map(ss: List<A>, f: (A) -> B): List<B> = TODO()

//Exercise 3.18
fun <A> filter(ls: List<A>, f: (A) -> Boolean): List<A> = TODO()

//Exercise 3.19
fun <A, B> flatMap(ss: List<A>, f: (A) -> List<B>): List<B> = TODO()


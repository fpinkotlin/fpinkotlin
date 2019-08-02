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

// tag::exercise3.7[]
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
// end::exercise3.7[]

// tag::exercise3.8[]
fun <A> length(xs: List<A>): Int = foldRight(xs, 0, { _, acc -> 1 + acc })
// end::exercise3.8[]

// tag::exercise3.9[]
tailrec fun <A, B> foldLeft(xs: List<A>, z: B, f: (B, A) -> B): B =
        when (xs) {
            is Nil -> z
            is Cons -> foldLeft(xs.tail, f(z, xs.head), f)
        }
// end::exercise3.9[]

// tag::exercise3.10[]
fun sumL(xs: List<Int>): Int = foldLeft(xs, 0, { x, y -> x + y })

fun productL(xs: List<Double>): Double = foldLeft(xs, 1.0, { x, y -> x * y })

fun <A> lengthL(xs: List<A>): Int = foldLeft(xs, 0, { acc, _ -> acc + 1 })
// end::exercise3.10[]

// tag::exercise3.11[]
fun <A> reverse(xs: List<A>): List<A> =
        foldLeft(xs, Nil as List<A>, { t: List<A>, h: A -> Cons(h, t) })
// end::exercise3.11[]

// tag::exercise3.12[]
// foldLeft processes items in the reverse order from foldRight.  It's
// cheating to use reverse() here because that's implemented in terms of
// foldLeft! Instead, wrap each operation in a simple identity function to
// delay evaluation until later and stack (nest) the functions so that the
// order of application can be reversed.  We'll call the type of this
// particular identity/delay function Identity<B> so we aren't writing B => B
// everywhere:
typealias Identity<B> = (B) -> B

fun <A, B> foldLeftR_2(ls: List<A>, outerIdentity: B, combiner: (B, A) -> B): B {

    // Here we declare a simple instance of BtoB according to the above
    // description.  This function will be the identity value for the inner
    // foldRight.
    fun innerIdentity(): Identity<B> = { b: B -> b }

    // For each item in the 'ls' list (the 'a' parameter below), make a new
    // delay function which will use the combiner function (passed in above)
    // when it is evaluated later.  Each new function becomes the input to the
    // previous delayExec function.
    //
    //                    This much is just the type signature
    //                     ,--------------^---------------,
    fun combinerDelayer(): (A, Identity<B>) -> Identity<B> =
            { a: A, delayExec: Identity<B> -> { b: B -> delayExec(combiner(b, a)) } }
    //        `------------v-------------'    `-----------------v-----------------'
    //                Parameters                 The returned function


    // Pass the original list 'ls', plus the simple identity function and the
    // new combinerDelayer to foldRight.  This will create the functions for
    // delayed evaluation with a combiner inside each one, but will not
    // invoke any of those functions.
    fun go(combinerDelayer: (A, Identity<B>) -> Identity<B>): Identity<B> =
            foldRight(ls, innerIdentity(), combinerDelayer)

    // This forces all the evaluations to take place
    return go(combinerDelayer()).invoke(outerIdentity)
}

fun <A, B> foldLeftR(xs: List<A>, z: B, f: (B, A) -> B): B =
        foldRight(xs, { b: B -> b }, { a, g -> { b -> g(f(b, a)) } })(z)

fun <A, B> foldRightL(xs: List<A>, z: B, f: (A, B) -> B): B =
        foldLeft(xs, { b: B -> b }, { g, a -> { b -> g(f(a, b)) } })(z)
// end::exercise3.12[]

// tag::exercise3.13[]
fun <A> appendR(a1: List<A>, a2: List<A>): List<A> =
        foldRight(a1, a2, { x, y -> Cons(x, y) })
// end::exercise3.13[]

// tag::exercise3.14[]
fun <A> concat(xxs: List<List<A>>): List<A> =
        foldRight(xxs, Nil as List<A>, { xs1: List<A>, xs2: List<A> ->
            foldRight(xs1, xs2, { a, ls -> Cons(a, ls) }) })
// end::exercise3.14[]

//Exercise 3.17
fun <A, B> map(ss: List<A>, f: (A) -> B): List<B> = TODO()

//Exercise 3.18
fun <A> filter(ls: List<A>, f: (A) -> Boolean): List<A> = TODO()

//Exercise 3.19
fun <A, B> flatMap(ss: List<A>, f: (A) -> List<B>): List<B> = TODO()


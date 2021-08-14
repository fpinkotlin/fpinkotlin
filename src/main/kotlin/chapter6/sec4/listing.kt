package chapter6.sec4

interface RNG {
    fun nextInt(): Pair<Int, RNG>
}

//tag::init1[]
typealias Rand<A> = (RNG) -> Pair<A, RNG>
//end::init1[]

//tag::init2[]
val intR: Rand<Int> = { rng -> rng.nextInt() }
//end::init2[]

//tag::init3[]
fun <A> unit(a: A): Rand<A> = { rng -> a to rng }
//end::init3[]

//tag::init4[]
fun <A, B> map(s: Rand<A>, f: (A) -> B): Rand<B> =
    { rng ->
        val (a, rng2) = s(rng)
        f(a) to rng2
    }
//end::init4[]

fun nonNegativeInt(rng: RNG): Pair<Int, RNG> = TODO()

//tag::init5[]
fun nonNegativeEven(): Rand<Int> =
    map(::nonNegativeInt) { it - (it % 2) }
//end::init5[]

fun <A, B, C> map2(
    ra: Rand<A>,
    rb: Rand<B>,
    f: (A, B) -> C
): Rand<C> = TODO()

//tag::init6[]
fun <A, B> both(ra: Rand<A>, rb: Rand<B>): Rand<Pair<A, B>> =
    map2(ra, rb) { a, b -> a to b }
//end::init6[]

//tag::init7[]
val doubleR: Rand<Double> =
    map(::nonNegativeInt) { i ->
        i / (Int.MAX_VALUE.toDouble() + 1)
    }

val intDoubleR: Rand<Pair<Int, Double>> = both(intR, doubleR)

val doubleIntR: Rand<Pair<Double, Int>> = both(doubleR, intR)
//end::init7[]

//tag::init8[]
fun nonNegativeLessThan(n: Int): Rand<Int> =
    map(::nonNegativeInt) { it % n }
//end::init8[]

/* This does not compile!
//tag::init9[]
fun nonNegativeLessThan(n: Int): Rand<Int> =
        map(::nonNegativeInt) { i ->
            val mod = i % n
            if (i + (n - 1) - mod >= 0) mod
            else nonNegativeLessThan(n)(???) <1> <2>
        }
//end::init9[]
 */

//tag::init10[]
fun nonNegativeIntLessThan(n: Int): Rand<Int> =
    { rng ->
        val (i, rng2) = nonNegativeInt(rng)
        val mod = i % n
        if (i + (n - 1) - mod >= 0)
            mod to rng2
        else nonNegativeIntLessThan(n)(rng2)
    }
//end::init10[]

//tag::init11[]
fun rollDie(): Rand<Int> =
    nonNegativeIntLessThan(6)
//end::init11[]

//tag::init12[]
fun rollDieFix(): Rand<Int> =
    map(nonNegativeIntLessThan(6)) { it + 1 }
//end::init12[]

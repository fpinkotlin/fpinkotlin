package chapter6

interface RNG {
    fun nextInt(): Pair<Int, RNG>
}

typealias Rand<A> = (RNG) -> Pair<A, RNG>

fun <A> unit(a: A): Rand<A> = { rng -> a to rng }

val unusedRng = object : RNG {
    override fun nextInt(): Pair<Int, RNG> = TODO()
}

val rng1 = object : RNG {
    override fun nextInt() = 1 to this
}

fun <A, B> map(s: Rand<A>, f: (A) -> B): Rand<B> =
    { rng ->
        val (a, rng2) = s(rng)
        f(a) to rng2
    }

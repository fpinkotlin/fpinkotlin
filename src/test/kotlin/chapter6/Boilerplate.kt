package chapter6

interface RNG {
    fun nextInt(): Pair<Int, RNG>
}

typealias Rand<A> = (RNG) -> Pair<A, RNG>

fun <A> unit(a: A): Rand<A> = { rng -> Pair(a, rng) }

val unusedRng = object : RNG {
    override fun nextInt(): Pair<Int, RNG> = TODO()
}

val rng1 = object : RNG {
    override fun nextInt() = Pair(1, this)
}

fun <A, B> map(s: Rand<A>, f: (A) -> B): Rand<B> =
    { rng ->
        val (a, rng2) = s(rng)
        Pair(f(a), rng2)
    }

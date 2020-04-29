package chapter10

import arrow.core.extensions.sequence.foldable.foldLeft
import arrow.core.extensions.set.foldable.foldLeft

interface Monoid<A> {
    fun op(a1: A, a2: A): A
    val zero: A
}

val stringMonoid = object : Monoid<String> {
    override fun op(a1: String, a2: String): String = a1 + a2

    override val zero: String
        get() = ""
}

fun <A> listMonoid(): Monoid<List<A>> = object : Monoid<List<A>> {
    override fun op(a1: List<A>, a2: List<A>): List<A> =
        List.append(a1, a2)

    override val zero: List<A>
        get() = List.empty()
}

fun <A> endoMonoid(): Monoid<(A) -> A> = object : Monoid<(A) -> A> {
    override fun op(a1: (A) -> A, a2: (A) -> A): (A) -> A =
        { a -> a1(a2(a)) }

    override val zero: (A) -> A
        get() = { a -> a }
}

val intAdditionMonoid: Monoid<Int> = object : Monoid<Int> {
    override fun op(a1: Int, a2: Int): Int = a1 + a2

    override val zero: Int
        get() = 0
}

val intMultiplicationMonoid: Monoid<Int> = object : Monoid<Int> {
    override fun op(a1: Int, a2: Int): Int = a1 * a2

    override val zero: Int
        get() = 1
}

fun <A> dual(m: Monoid<A>): Monoid<A> = object : Monoid<A> {
    override fun op(a1: A, a2: A): A =
        m.op(a2, a1)

    override val zero: A
        get() = m.zero
}

fun <K, V> mapMergeMonoid(v: Monoid<V>): Monoid<Map<K, V>> =
    object : Monoid<Map<K, V>> {
        override fun op(a1: Map<K, V>, a2: Map<K, V>): Map<K, V> =
            (a1.keys + a2.keys).foldLeft(zero, { acc, k ->
                acc + mapOf(
                    k to v.op(
                        a1.getOrDefault(k, v.zero),
                        a2.getOrDefault(k, v.zero)
                    )
                )
            })

        override val zero: Map<K, V>
            get() = emptyMap()
    }

fun <A, B> foldMap(la: Sequence<A>, m: Monoid<B>, f: (A) -> B): B =
    la.map(f).foldLeft(m.zero, m::op)

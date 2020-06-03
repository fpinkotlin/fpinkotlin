package chapter10

import arrow.core.extensions.sequence.foldable.foldLeft
import arrow.core.extensions.set.foldable.foldLeft

interface Monoid<A> {
    fun combine(a1: A, a2: A): A
    val nil: A
}

val stringMonoid = object : Monoid<String> {
    override fun combine(a1: String, a2: String): String = a1 + a2

    override val nil: String
        get() = ""
}

fun <A> listMonoid(): Monoid<List<A>> = object : Monoid<List<A>> {
    override fun combine(a1: List<A>, a2: List<A>): List<A> =
        List.append(a1, a2)

    override val nil: List<A>
        get() = List.empty()
}

fun <A> endoMonoid(): Monoid<(A) -> A> = object : Monoid<(A) -> A> {
    override fun combine(a1: (A) -> A, a2: (A) -> A): (A) -> A =
        { a -> a1(a2(a)) }

    override val nil: (A) -> A
        get() = { a -> a }
}

val intAdditionMonoid: Monoid<Int> = object : Monoid<Int> {
    override fun combine(a1: Int, a2: Int): Int = a1 + a2

    override val nil: Int
        get() = 0
}

val intMultiplicationMonoid: Monoid<Int> = object : Monoid<Int> {
    override fun combine(a1: Int, a2: Int): Int = a1 * a2

    override val nil: Int
        get() = 1
}

fun <A> dual(m: Monoid<A>): Monoid<A> = object : Monoid<A> {
    override fun combine(a1: A, a2: A): A =
        m.combine(a2, a1)

    override val nil: A
        get() = m.nil
}

fun <K, V> mapMergeMonoid(v: Monoid<V>): Monoid<Map<K, V>> =
    object : Monoid<Map<K, V>> {
        override fun combine(a1: Map<K, V>, a2: Map<K, V>): Map<K, V> =
            (a1.keys + a2.keys).foldLeft(nil, { acc, k ->
                acc + mapOf(
                    k to v.combine(
                        a1.getOrDefault(k, v.nil),
                        a2.getOrDefault(k, v.nil)
                    )
                )
            })

        override val nil: Map<K, V>
            get() = emptyMap()
    }

fun <A, B> foldMap(la: Sequence<A>, m: Monoid<B>, f: (A) -> B): B =
    la.map(f).foldLeft(m.nil, m::combine)

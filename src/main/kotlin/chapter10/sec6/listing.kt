package chapter10.sec6

import arrow.core.extensions.set.foldable.foldLeft
import chapter10.sec1.Monoid

//tag::init1[]
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

        override val nil: Map<K, V> = emptyMap()
    }
//end::init1[]

val intAdditionMonoid: Monoid<Int> = object : Monoid<Int> {
    override fun combine(a1: Int, a2: Int): Int = a1 + a2

    override val nil: Int = 0
}

//tag::init2[]
val m: Monoid<Map<String, Map<String, Int>>> =
    mapMergeMonoid<String, Map<String, Int>>(
        mapMergeMonoid<String, Int>(
            intAdditionMonoid
        )
    )
//end::init2[]

val m1 = mapOf("o1" to mapOf("i1" to 1, "i2" to 2))
val m2 = mapOf("o1" to mapOf("i3" to 3))

val m3 = m.combine(m1, m2)
//kotlin.collections.Map<
//  kotlin.String,
//  kotlin.collections.Map<
//    kotlin.String,
//    kotlin.Int>> =
//      {o1={i1=1, i2=2, i3=3}}

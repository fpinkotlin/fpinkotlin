package chapter14.solutions.ex3

import chapter10.Option
import chapter14.boilerplate.ST
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap

//tag::init[]
abstract class STMap<S, K, V> @PublishedApi internal constructor() {
    companion object {
        inline operator fun <S, reified K, reified V> invoke():
            ST<S, STMap<S, K, V>> =
            ST {
                object : STMap<S, K, V>() {
                    override val map: MutableMap<K, V> = mutableMapOf()
                }
            }

        fun <S, K, V> fromMap(map: Map<K, V>): ST<S, STMap<S, K, V>> =
            ST {
                object : STMap<S, K, V>() {
                    override val map: MutableMap<K, V> = map.toMutableMap()
                }
            }
    }

    protected abstract val map: MutableMap<K, V>

    val size: ST<S, Int> = ST { map.size }

    fun get(k: K): ST<S, V> = object : ST<S, V>() {
        override fun run(s: S): Pair<V, S> =
            map.getOrElse(k, noElementFor(k)) to s
    }

    fun getOption(k: K): ST<S, Option<V>> = object : ST<S, Option<V>>() {
        override fun run(s: S): Pair<Option<V>, S> =
            Option.of(map[k]) to s
    }

    fun put(k: K, v: V): ST<S, Unit> = object : ST<S, Unit>() {
        override fun run(s: S): Pair<Unit, S> {
            map[k] = v
            return Unit to s
        }
    }

    fun remove(k: K): ST<S, Unit> = object : ST<S, Unit>() {
        override fun run(s: S): Pair<Unit, S> {
            map.remove(k)
            return Unit to s
        }
    }

    fun clear(): ST<S, Unit> = object : ST<S, Unit>() {
        override fun run(s: S): Pair<Unit, S> {
            map.clear()
            return Unit to s
        }
    }

    private fun noElementFor(k: K): () -> Nothing =
        { throw NoSuchElementException("no value for key: $k") }

    fun freeze(): ST<S, ImmutableMap<K, V>> =
        ST { map.toImmutableMap() }
}
//end::init[]

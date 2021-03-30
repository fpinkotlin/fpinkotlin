package chapter14.boilerplate

import arrow.core.Either
import arrow.extension
import arrow.typeclasses.Monad
import arrow.typeclasses.MonadSyntax
import chapter14.boilerplate.st.monad.monad

class ForST private constructor() {
    companion object
}

typealias STOf<S, A> = arrow.Kind2<ForST, S, A>

typealias STPartialOf<S> = arrow.Kind<ForST, S>

@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
inline fun <S, A> STOf<S, A>.fix(): ST<S, A> = this as ST<S, A>

abstract class ST<S, A> internal constructor() : STOf<S, A> {
    companion object {
        operator fun <S, A> invoke(a: () -> A): ST<S, A> {
            val memo by lazy(a)
            return object : ST<S, A>() {
                override fun run(s: S) = memo to s
            }
        }

        fun <A> runST(st: RunnableST<A>): A =
            st.invoke<Unit>().run(Unit).first
    }

    protected abstract fun run(s: S): Pair<A, S>

    fun <B> map(f: (A) -> B): ST<S, B> = object : ST<S, B>() {
        override fun run(s: S): Pair<B, S> {
            val (a, s1) = this@ST.run(s)
            return f(a) to s1
        }
    }

    fun <B> flatMap(f: (A) -> ST<S, B>): ST<S, B> = object : ST<S, B>() {
        override fun run(s: S): Pair<B, S> {
            val (a, s1) = this@ST.run(s)
            return f(a).run(s1)
        }
    }
}

interface RunnableST<A> {
    fun <S> invoke(): ST<S, A>
}

abstract class STRef<S, A> private constructor() {
    companion object {
        operator fun <S, A> invoke(a: A): ST<S, STRef<S, A>> = ST {
            object : STRef<S, A>() {
                override var cell: A = a
            }
        }
    }

    protected abstract var cell: A

    fun read(): ST<S, A> = ST {
        cell
    }

    fun write(a: A): ST<S, Unit> = object : ST<S, Unit>() {
        override fun run(s: S): Pair<Unit, S> {
            cell = a
            return Unit to s
        }
    }
}

abstract class STArray<S, A> @PublishedApi internal constructor() {

    companion object {
        inline operator fun <S, reified A> invoke(
            sz: Int,
            v: A
        ): ST<S, STArray<S, A>> = ST {
            object : STArray<S, A>() {
                override val value = Array(sz) { v }
            }
        }

        inline fun <S, reified A> fromList(
            xs: List<A>
        ): ST<S, STArray<S, A>> =
            ST {
                object : STArray<S, A>() {
                    override val value: Array<A> = xs.toTypedArray()
                }
            }
    }

    protected abstract val value: Array<A>

    val size: ST<S, Int> = ST { value.size }

    fun write(i: Int, a: A): ST<S, Unit> = object : ST<S, Unit>() {
        override fun run(s: S): Pair<Unit, S> {
            value[i] = a
            return Unit to s
        }
    }

    fun read(i: Int): ST<S, A> = ST { value[i] }

    fun freeze(): ST<S, List<A>> = ST { value.toList() }

    fun fill(xs: Map<Int, A>): ST<S, Unit> =
        xs.entries.fold(ST { Unit }) { st, (k, v) ->
            st.flatMap { write(k, v) }
        }

    fun swap(i: Int, j: Int): ST<S, Unit> =
        read(i).flatMap { x ->
            read(j).flatMap { y ->
                write(i, y).flatMap {
                    write(j, x)
                }
            }
        }
}

@extension
interface STMonad<S, A> : Monad<STPartialOf<S>> {

    override fun <A> just(a: A): STOf<S, A> = ST { a }

    override fun <A, B> STOf<S, A>.flatMap(
        f: (A) -> STOf<S, B>
    ): STOf<S, B> =
        this.fix().flatMap { a -> f(a).fix() }

    override fun <A, B> tailRecM(
        a: A,
        f: (A) -> STOf<S, Either<A, B>>
    ): STOf<S, B> = TODO()
}

fun <S, A> ST.Companion.fx(
    c: suspend MonadSyntax<STPartialOf<S>>.() -> A
): ST<S, A> =
    ST.monad<S, A>().fx.monad(c).fix()

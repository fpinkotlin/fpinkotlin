package chapter14.sec2

import arrow.core.Either
import arrow.extension
import arrow.typeclasses.Monad
import arrow.typeclasses.MonadSyntax
import chapter14.sec2.st.monad.monad

class ForST private constructor() {
    companion object
}

//tag::typealias[]
typealias STOf<S, A> = arrow.Kind2<ForST, S, A>
//end::typealias[]

typealias STPartialOf<S> = arrow.Kind<ForST, S>

@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
inline fun <S, A> STOf<S, A>.fix(): ST<S, A> = this as ST<S, A>

//tag::init1[]
abstract class ST<S, A> internal constructor() : STOf<S, A> { // <1>
    companion object {
        operator fun <S, A> invoke(a: () -> A): ST<S, A> {
            val memo by lazy(a) // <2>
            return object : ST<S, A>() {
                override fun run(s: S) = memo to s
            }
        }

        //tag::init6[]
        fun <A> runST(st: RunnableST<A>): A =
            st.invoke<Unit>().run(Unit).first
        //end::init6[]
    }

    protected abstract fun run(s: S): Pair<A, S>

    fun <B> map(f: (A) -> B): ST<S, B> = object : ST<S, B>() {
        override fun run(s: S): Pair<B, S> {
            val (a, s1) = this@ST.run(s) // <3>
            return f(a) to s1
        }
    }

    fun <B> flatMap(f: (A) -> ST<S, B>): ST<S, B> = object : ST<S, B>() {
        override fun run(s: S): Pair<B, S> {
            val (a, s1) = this@ST.run(s) // <3>
            return f(a).run(s1)
        }
    }
}
//end::init1[]

//tag::init2[]
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
//end::init2[]

//tag::init3[]
val p1 =
    STRef<Nothing, Int>(10).flatMap { r1 ->
        STRef<Nothing, Int>(20).flatMap { r2 ->
            r1.read().flatMap { x ->
                r2.read().flatMap { y ->
                    r1.write(y + 1).flatMap {
                        r2.write(x + 1).flatMap {
                            r1.read().flatMap { a ->
                                r2.read().map { b ->
                                    a to b
                                }
                            }
                        }
                    }
                }
            }
        }
    }
//end::init3[]

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

//tag::init3b[]
val p2 =
    ST.fx<Nothing, Pair<Int, Int>> {
        val r1 = STRef<Nothing, Int>(10).bind()
        val r2 = STRef<Nothing, Int>(20).bind()
        val x = r1.read().bind()
        val y = r2.read().bind()
        r1.write(y + 1).bind()
        r2.write(x + 1).bind()
        val a = r1.read().bind()
        val b = r2.read().bind()
        a to b
    }
//end::init3b[]

//tag::init4[]
interface RunnableST<A> {
    fun <S> invoke(): ST<S, A>
}
//end::init4[]

//tag::init5[]
val p3 = object : RunnableST<Pair<Int, Int>> {
    override fun <S> invoke(): ST<S, Pair<Int, Int>> =
        ST.fx {
            val r1 = STRef<S, Int>(10).bind()
            val r2 = STRef<S, Int>(20).bind()
            val x = r1.read().bind()
            val y = r2.read().bind()
            r1.write(y + 1).bind()
            r2.write(x + 1).bind()
            val a = r1.read().bind()
            val b = r2.read().bind()
            a to b
        }
}
//end::init5[]

//tag::init7[]
abstract class STArray<S, A> @PublishedApi internal constructor() { // <1>

    companion object {
        inline operator fun <S, reified A> invoke( // <2>
            sz: Int,
            v: A
        ): ST<S, STArray<S, A>> = ST {
            object : STArray<S, A>() {
                override val value = Array(sz) { v }
            }
        }

        //tag::init8[]
        inline fun <S, reified A> fromList(
            xs: List<A>
        ): ST<S, STArray<S, A>> =
            ST {
                object : STArray<S, A>() {
                    override val value: Array<A> = xs.toTypedArray()
                }
            }
        //end::init8[]
    }

    protected abstract val value: Array<A> // <3>

    val size: ST<S, Int> = ST { value.size }

    fun write(i: Int, a: A): ST<S, Unit> = object : ST<S, Unit>() { // <4>
        override fun run(s: S): Pair<Unit, S> {
            value[i] = a
            return Unit to s
        }
    }

    fun read(i: Int): ST<S, A> = ST { value[i] } // <5>

    fun freeze(): ST<S, List<A>> = ST { value.toList() } // <6>

    //tag::init9[]
    fun swap(i: Int, j: Int): ST<S, Unit> =
        read(i).flatMap { x ->
            read(j).flatMap { y ->
                write(i, y).flatMap {
                    write(j, x)
                }
            }
        }
    //end::init9[]
}
//end::init7[]

object Immutable {
    private fun <S> partition(
        arr: STArray<S, Int>,
        l: Int,
        r: Int,
        pivot: Int
    ): ST<S, Int> =
        ST.fx {
            val vp = arr.read(pivot).bind()
            arr.swap(pivot, r).bind()
            val j = STRef<S, Int>(l).bind()
            (l until r).fold(noop<S>()) { st, i: Int ->
                st.bind()
                val vi = arr.read(i).bind()
                if (vi < vp) {
                    val vj = j.read().bind()
                    arr.swap(i, vj).bind()
                    j.write(vj + 1)
                } else noop()
            }.bind()
            val x = j.read().bind()
            arr.swap(x, r).bind()
            x
        }

    private fun <S> qs(arr: STArray<S, Int>, l: Int, r: Int): ST<S, Unit> =
        if (l < r)
            partition(arr, l, r, l + (r - l) / 2).flatMap { pi ->
                qs(arr, l, pi - 1).flatMap {
                    qs(arr, pi + 1, r)
                }
            } else noop()

    private fun <S> noop() = ST<S, Unit> { Unit }

    //tag::init10[]
    fun quicksort(xs: List<Int>): List<Int> =
        if (xs.isEmpty()) xs else ST.runST(object : RunnableST<List<Int>> {
            override fun <S> invoke(): ST<S, List<Int>> =
                ST.fx {
                    val arr = STArray.fromList<S, Int>(xs).bind()
                    val size = arr.size.bind()
                    qs(arr, 0, size - 1).bind()
                    arr.freeze().bind()
                }
        })
    //end::init10[]
}

fun main() {
    println(ST.runST(p3))
    println(Immutable.quicksort(listOf(9, 8, 7, 6, 5, 4, 3, 2, 1)))
}

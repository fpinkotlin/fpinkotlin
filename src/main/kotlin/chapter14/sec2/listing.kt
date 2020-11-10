package chapter14.sec2

//tag::init1[]
abstract class ST<S, A> internal constructor() { // <1>
    companion object {
        operator fun <S, A> invoke(a: () -> A): ST<S, A> {
            val memo by lazy(a) // <2>
            return object : ST<S, A>() {
                override fun run(s: S) = Pair(memo, s)
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
            return Pair(f(a), s1)
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
            return Pair(Unit, s)
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
                                    Pair(a, b)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
//end::init3[]

//tag::init4[]
interface RunnableST<A> {
    fun <S> invoke(): ST<S, A>
}
//end::init4[]

//tag::init5[]
val p2 = object : RunnableST<Pair<Int, Int>> {
    override fun <S> invoke(): ST<S, Pair<Int, Int>> =
        STRef<S, Int>(10).flatMap { r1: STRef<S, Int> ->
            STRef<S, Int>(20).flatMap { r2: STRef<S, Int> ->
                r1.read().flatMap { x ->
                    r2.read().flatMap { y ->
                        r1.write(y + 1).flatMap {
                            r2.write(x + 1).flatMap {
                                r1.read().flatMap { a ->
                                    r2.read().map { b ->
                                        Pair(a, b)
                                    }
                                }
                            }
                        }
                    }
                }
            }
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
        inline fun <S, reified A> fromList(xs: List<A>): ST<S, STArray<S, A>> =
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
            return Pair(Unit, s)
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
        arr.read(pivot).flatMap { vp ->
            arr.swap(pivot, r).flatMap {
                STRef<S, Int>(l).flatMap { j ->
                    (l until r).fold(noop<S>()) { st, i: Int ->
                        st.flatMap {
                            arr.read(i).flatMap { vi ->
                                if (vi < vp) {
                                    j.read().flatMap { vj ->
                                        arr.swap(i, vj).flatMap {
                                            j.write(vj + 1)
                                        }
                                    }
                                } else noop()
                            }
                        }
                    }.flatMap {
                        j.read().flatMap { x ->
                            arr.swap(x, r).map { x }
                        }
                    }
                }
            }
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
                STArray.fromList<S, Int>(xs).flatMap { arr ->
                    arr.size.flatMap { size ->
                        qs(arr, 0, size - 1).flatMap {
                            arr.freeze().map { it }
                        }
                    }
                }
        })
    //end::init10[]
}

fun main() {
    println(Immutable.quicksort(listOf(9, 8, 7, 6, 5, 4, 3, 2, 1)))
}
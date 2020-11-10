package chapter14.boilerplate

interface RunnableST<A> {
    fun <S> invoke(): ST<S, A>
}

abstract class ST<S, A> internal constructor() {
    companion object {
        operator fun <S, A> invoke(a: () -> A): ST<S, A> {
            val memo by lazy(a)
            return object : ST<S, A>() {
                override fun run(s: S) = Pair(memo, s)
            }
        }

        fun <A> runST(st: RunnableST<A>): A =
            st.invoke<Unit>().run(Unit).first
    }

    protected abstract fun run(s: S): Pair<A, S>

    fun <B> map(f: (A) -> B): ST<S, B> = object : ST<S, B>() {
        override fun run(s: S): Pair<B, S> {
            val (a, s1) = this@ST.run(s)
            return Pair(f(a), s1)
        }
    }

    fun <B> flatMap(f: (A) -> ST<S, B>): ST<S, B> = object : ST<S, B>() {
        override fun run(s: S): Pair<B, S> {
            val (a, s1) = this@ST.run(s)
            return f(a).run(s1)
        }
    }
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
            return Pair(Unit, s)
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

        inline fun <S, reified A> fromList(xs: List<A>): ST<S, STArray<S, A>> =
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
            return Pair(Unit, s)
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
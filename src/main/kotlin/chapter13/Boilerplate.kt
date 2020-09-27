package chapter13

import arrow.higherkind

data class IORef<A>(var value: A) {
    fun set(a: A): IO<A> = IO {value = a; a}
    fun get(): IO<A> = IO { value }
    fun modify(f: (A) -> A): IO<A> = get().flatMap { a -> set(f(a)) }
}

@higherkind
interface IO<A> : IOOf<A> {

    companion object {

        fun <A> unit(a: () -> A) = object : IO<A> {
            override fun run(): A = a()
        }

        operator fun <A> invoke(a: () -> A) = unit(a)

        fun ref(i: Int): IO<IORef<Int>> = IO { IORef(i) }
    }

    fun run(): A

    fun <B> map(f: (A) -> B): IO<B> =
        object : IO<B> {
            override fun run(): B = f(this@IO.run())
        }

    fun <B> flatMap(f: (A) -> IO<B>): IO<B> =
        object : IO<B> {
            override fun run(): B = f(this@IO.run()).run()
        }

    infix fun <B> product(io: IO<B>): IO<Pair<A, B>> =
        object : IO<Pair<A, B>> {
            override fun run(): Pair<A, B> =
                Pair(this@IO.run(), io.run())
        }
}

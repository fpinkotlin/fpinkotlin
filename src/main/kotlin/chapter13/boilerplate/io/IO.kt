package chapter13.boilerplate.io

import arrow.Kind
import arrow.extension
import chapter13.Monad
import chapter5.Cons
import chapter5.Empty
import chapter5.Stream

data class IORef<A>(var value: A) {
    fun set(a: A): IO<A> = IO { value = a; a }
    fun get(): IO<A> = IO { value }
    fun modify(f: (A) -> A): IO<A> = get().flatMap { a -> set(f(a)) }
}

class ForIO {
    companion object
}
typealias IOOf<A> = Kind<ForIO, A>

inline fun <A> IOOf<A>.fix(): IO<A> = this as IO<A>

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
                this@IO.run() to io.run()
        }
}

@extension
interface IOMonad : Monad<ForIO> {

    override fun <A> unit(a: A): IOOf<A> =
        IO.unit { a }.fix()

    override fun <A, B> flatMap(
        fa: IOOf<A>,
        f: (A) -> IOOf<B>
    ): IOOf<B> =
        fa.fix().flatMap { a -> f(a).fix() }

    override fun <A, B> map(
        fa: Kind<ForIO, A>,
        f: (A) -> B
    ): Kind<ForIO, B> =
        flatMap(fa.fix()) { a -> unit(f(a)) }

    fun <A> doWhile(
        fa: IOOf<A>,
        cond: (A) -> IOOf<Boolean>
    ): IOOf<Unit> =
        fa.fix().flatMap { a: A ->
            cond(a).fix().flatMap { ok: Boolean ->
                if (ok) doWhile(fa, cond).fix() else unit(Unit).fix()
            }
        }

    fun <A, B> forever(fa: IOOf<A>): IOOf<B> {
        val t: IOOf<B> by lazy { forever<A, B>(fa) }
        return fa.fix().flatMap { t.fix() }
    }

    fun <A, B> foldM(
        sa: Stream<A>,
        z: B,
        f: (B, A) -> IOOf<B>
    ): IOOf<B> =
        when (sa) {
            is Cons ->
                f(z, sa.head()).fix().flatMap { b ->
                    foldM(sa.tail(), z, f).fix()
                }
            is Empty -> unit(z)
        }

    fun <A, B> foldDiscardM(
        sa: Stream<A>,
        z: B,
        f: (B, A) -> Kind<ForIO, B>
    ): Kind<ForIO, Unit> =
        foldM(sa, z, f).fix().map { Unit }

    fun <A> foreachM(
        sa: Stream<A>,
        f: (A) -> IOOf<Unit>
    ): IOOf<Unit> =
        foldDiscardM(sa, Unit) { _, a -> f(a) }

    fun <A> whenM(
        ok: Boolean,
        f: () -> IOOf<A>
    ): IOOf<Boolean> =
        if (ok) f().fix().map { true } else unit(false)
}

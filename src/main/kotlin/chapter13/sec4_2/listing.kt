package chapter13.sec4_2

import arrow.Kind
import chapter11.ForPar
import chapter11.Monad
import chapter11.Par
import chapter11.ParOf
import chapter11.fix
import chapter13.boilerplate.free.FlatMap
import chapter13.boilerplate.free.Free
import chapter13.boilerplate.free.Return
import chapter13.boilerplate.free.Suspend
import chapter13.boilerplate.function.ForFunction0
import chapter13.boilerplate.function.Function0
import chapter13.boilerplate.function.Function0Of
import chapter13.boilerplate.function.fix
import chapter13.sec4.console.ForConsole
import chapter13.sec4.console.fix

fun <F, A> step(free: Free<F, A>): Free<F, A> = TODO()

//tag::init1[]
interface Translate<F, G> {
    operator fun <A> invoke(fa: Kind<F, A>): Kind<G, A>
}

fun consoleToFunction0() = object : Translate<ForConsole, ForFunction0> {
    override fun <A> invoke(
        fa: Kind<ForConsole, A>
    ): Kind<ForFunction0, A> =
        Function0(fa.fix().toThunk())
}

fun consoleToPar() = object : Translate<ForConsole, ForPar> {
    override fun <A> invoke(
        fa: Kind<ForConsole, A>
    ): Kind<ForPar, A> =
        fa.fix().toPar()
}
//end::init1[]

@Suppress("UNCHECKED_CAST")
//tag::init2[]
fun <F, G, A> runFree(
    free: Free<F, A>,
    t: Translate<F, G>,
    MG: Monad<G>
): Kind<G, A> =
    when (val stepped = step(free)) {
        is Return -> MG.unit(stepped.a)
        is Suspend -> t(stepped.resume)
        is FlatMap<*, *, *> -> {
            val sub = stepped.sub as Free<F, A>
            val f = stepped.f as (A) -> Free<F, A>
            when (sub) {
                is Suspend ->
                    MG.flatMap(t(sub.resume)) { a -> runFree(f(a), t, MG) }
                else -> throw RuntimeException(
                    "Impossible, step eliminates such cases"
                )
            }
        }
    }
//end::init2[]

//tag::init4[]
fun functionMonad() = object : Monad<ForFunction0> {
    override fun <A> unit(a: A): Function0Of<A> = Function0 { a }
    override fun <A, B> flatMap(
        fa: Function0Of<A>,
        f: (A) -> Function0Of<B>
    ): Function0Of<B> = { f(fa.fix().f()) }()
}

fun parMonad() = object : Monad<ForPar> {
    override fun <A> unit(a: A): ParOf<A> = Par.unit(a)

    override fun <A, B> flatMap(
        fa: ParOf<A>,
        f: (A) -> ParOf<B>
    ): ParOf<B> = fa.fix().flatMap { a -> f(a).fix() }
}
//end::init4[]

//tag::init3[]
fun <A> runConsoleFunction0(a: Free<ForConsole, A>): Function0<A> =
    runFree(a, consoleToFunction0(), functionMonad()).fix()

fun <A> runConsolePar(a: Free<ForConsole, A>): Par<A> =
    runFree(a, consoleToPar(), parMonad()).fix()
//end::init3[]

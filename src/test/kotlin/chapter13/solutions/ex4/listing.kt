package chapter13.solutions.ex4

import arrow.Kind
import chapter11.Monad
import chapter13.boilerplate.free.Free
import chapter13.boilerplate.free.FreeOf
import chapter13.boilerplate.free.FreePartialOf
import chapter13.boilerplate.free.Return
import chapter13.boilerplate.free.Suspend
import chapter13.boilerplate.free.fix
import chapter13.boilerplate.function.ForFunction0
import chapter13.boilerplate.function.Function0
import chapter13.boilerplate.function.Function0Of
import chapter13.sec4.console.ConsoleOf
import chapter13.sec4.console.ForConsole
import chapter13.sec4.console.fix
import chapter13.sec4_2.Translate
import chapter13.sec4_2.runFree
import chapter13.solutions.ex2.runTrampoline

fun <F> freeMonad() = object : Monad<FreePartialOf<F>> {
    override fun <A, B> map(
        fa: FreeOf<F, A>,
        f: (A) -> B
    ): FreeOf<F, B> =
        flatMap(fa) { a -> unit(f(a)) }

    override fun <A> unit(a: A): FreeOf<F, A> =
        Return(a)

    override fun <A, B> flatMap(
        fa: FreeOf<F, A>,
        f: (A) -> FreeOf<F, B>
    ): FreeOf<F, B> =
        fa.fix().flatMap { a -> f(a).fix() }
}

//tag::init1[]
fun <F, G, A> translate(
    free: Free<F, A>,
    translate: Translate<F, G>
): Free<G, A> {
    val t = object : Translate<F, FreePartialOf<G>> {
        override fun <A> invoke(
            fa: Kind<F, A>
        ): Kind<FreePartialOf<G>, A> = Suspend(translate(fa))
    }
    return runFree(free, t, freeMonad()).fix()
}

fun <A> runConsole(a: Free<ForConsole, A>): A {
    val t = object : Translate<ForConsole, ForFunction0> {
        override fun <A> invoke(ca: ConsoleOf<A>): Function0Of<A> =
            Function0(ca.fix().toThunk())
    }
    return runTrampoline(translate(a, t))
}
//end::init1[]

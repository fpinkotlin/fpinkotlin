package chapter13.sec5

import arrow.Kind
import chapter10.None
import chapter10.Some
import chapter11.ForPar
import chapter11.Monad
import chapter11.Par
import chapter11.ParOf
import chapter11.fix
import chapter12.Either
import chapter13.boilerplate.console.Console
import chapter13.boilerplate.console.ConsoleIO
import chapter13.boilerplate.console.ForConsole
import chapter13.boilerplate.console.fix
import chapter13.boilerplate.free.Free
import chapter13.sec4_2.Translate
import chapter13.sec4_2.runFree

fun consoleToPar() = object : Translate<ForConsole, ForPar> {
    override fun <A> invoke(
        fa: Kind<ForConsole, A>
    ): Kind<ForPar, A> = fa.fix().toPar()
}

fun parMonad() = object : Monad<ForPar> {
    override fun <A> unit(a: A): ParOf<A> = Par.unit(a)

    override fun <A, B> flatMap(
        fa: ParOf<A>,
        f: (A) -> ParOf<B>
    ): ParOf<B> = fa.fix().flatMap { a -> f(a).fix() }
}

fun <A> runConsolePar(a: Free<ForConsole, A>): Par<A> =
    runFree(a, consoleToPar(), parMonad()).fix()

//tag::init1[]
val p: ConsoleIO<Unit> =
    Console.stdout("What's your name").flatMap {
        Console.stdin().map { n ->
            when (n) {
                is Some<String> ->
                    println("Hello, ${n.get}!")
                is None ->
                    println("Fine, be that way!")
            }
        }
    }

val result: Par<Unit> = runConsolePar(p)
//end::init1[]

//tag::init2[]
interface Source {
    fun readBytes(
        numBytes: Int,
        callback: (Either<Throwable, Array<Byte>>) -> Unit
    ): Unit
}
//end::init2[]

package chapter13.sec4_3

import arrow.extension
import chapter11.Monad
import chapter13.sec4.console.ConsoleIO
import chapter13.sec4.console.ConsoleOf
import chapter13.sec4.console.ForConsole
import chapter13.sec4.console.fix
import chapter13.sec4_2.Translate
import chapter13.sec4_2.runFree
import chapter13.sec4_3.consolereader.monad.monad
import chapter13.sec4_3.consolestate.monad.monad

class ForConsoleReader private constructor() {
    companion object
}
typealias ConsoleReaderOf<A> = arrow.Kind<ForConsoleReader, A>

@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
inline fun <A> ConsoleReaderOf<A>.fix(): ConsoleReader<A> =
    this as ConsoleReader<A>

//tag::init1[]
data class ConsoleReader<A>(val run: (String) -> A) : ConsoleReaderOf<A> {

    companion object // <.>

    fun <B> flatMap(f: (A) -> ConsoleReader<B>): ConsoleReader<B> =
        ConsoleReader { r -> f(run(r)).run(r) } // <.>

    fun <B> map(f: (A) -> B): ConsoleReader<B> =
        ConsoleReader { r -> f(run(r)) } // <.>
}

@extension // <.>
interface ConsoleReaderMonad : Monad<ForConsoleReader> { // <.>

    override fun <A> unit(a: A): ConsoleReaderOf<A> =
        ConsoleReader { a }

    override fun <A, B> flatMap(
        fa: ConsoleReaderOf<A>,
        f: (A) -> ConsoleReaderOf<B>
    ): ConsoleReaderOf<B> =
        fa.fix().flatMap { a -> f(a).fix() } // <.>

    override fun <A, B> map(
        fa: ConsoleReaderOf<A>,
        f: (A) -> B
    ): ConsoleReaderOf<B> =
        fa.fix().map(f) // <.>
}
//end::init1[]

//tag::init2[]
val consoleToConsoleReader =
    object : Translate<ForConsole, ForConsoleReader> {
        override fun <A> invoke(fa: ConsoleOf<A>): ConsoleReaderOf<A> =
            fa.fix().toReader() // <1>
    }

fun <A> runConsoleReader(cio: ConsoleIO<A>): ConsoleReader<A> =
    runFree(cio, consoleToConsoleReader, ConsoleReader.monad()).fix() //<2>
//end::init2[]

class ForConsoleState private constructor() {
    companion object
}
typealias ConsoleStateOf<A> = arrow.Kind<ForConsoleState, A>

@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
inline fun <A> ConsoleStateOf<A>.fix(): ConsoleState<A> =
    this as ConsoleState<A>

//tag::init3[]
data class Buffers( // <1>
    val input: List<String>, // <2>
    val output: List<String> // <3>
)

data class ConsoleState<A>(
    val run: (Buffers) -> Pair<A, Buffers>
) : ConsoleStateOf<A> { // <4>
    // implement flatMap and map here
    //tag::ignore[]
    companion object

    fun <B> flatMap(f: (A) -> ConsoleState<B>): ConsoleState<B> =
        ConsoleState { bs1: Buffers ->
            val (a, bs2) = run(bs1)
            f(a).run(bs2)
        }

    fun <B> map(f: (A) -> B): ConsoleState<B> =
        ConsoleState { bs1: Buffers ->
            val (a, bs2) = run(bs1)
            f(a) to bs2
        }
    //end::ignore[]
}

@extension
interface ConsoleStateMonad : Monad<ForConsoleState> {
    // override unit and flatMap here
    //tag::ignore[]
    override fun <A> unit(a: A): ConsoleStateOf<A> =
        ConsoleState { b: Buffers -> a to b }

    override fun <A, B> flatMap(
        fa: ConsoleStateOf<A>,
        f: (A) -> ConsoleStateOf<B>
    ): ConsoleStateOf<B> =
        fa.fix().flatMap { a -> f(a).fix() }

    override fun <A, B> map(
        fa: ConsoleStateOf<A>,
        f: (A) -> B
    ): ConsoleStateOf<B> =
        fa.fix().map(f)
    //end::ignore[]
}
//end::init3[]

//tag::init4[]
val consoleToConsoleState =
    object : Translate<ForConsole, ForConsoleState> {
        override fun <A> invoke(fa: ConsoleOf<A>): ConsoleStateOf<A> =
            fa.fix().toState()
    }

fun <A> runConsoleState(cio: ConsoleIO<A>): ConsoleState<A> = // <5>
    runFree(cio, consoleToConsoleState, ConsoleState.monad()).fix()
//end::init4[]

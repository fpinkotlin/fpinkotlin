package chapter13.boilerplate.console

import chapter10.None
import chapter10.Option
import chapter10.Some
import chapter11.Par
import chapter13.boilerplate.free.Free
import chapter13.boilerplate.free.Suspend

typealias ConsoleIO<A> = Free<ForConsole, A>

//Console

class ForConsole private constructor() {
    companion object
}

typealias ConsoleOf<A> = arrow.Kind<ForConsole, A>

@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
inline fun <A> ConsoleOf<A>.fix(): Console<A> = this as Console<A>

sealed class Console<A> : ConsoleOf<A> {
    abstract fun toState(): ConsoleState<A>

    abstract fun toPar(): Par<A>

    abstract fun toThunk(): () -> A

    companion object {
        fun stdin(): ConsoleIO<Option<String>> =
            Suspend(ReadLine)

        fun stdout(line: String): ConsoleIO<Unit> =
            Suspend(PrintLine(line))
    }
}

object ReadLine : Console<Option<String>>() {

    override fun toPar(): Par<Option<String>> = Par.unit(run())

    override fun toThunk(): () -> Option<String> = { run() }

    fun run(): Option<String> = // <3>
        try {
            Some(readLine().orEmpty())
        } catch (e: Exception) {
            None
        }

    override fun toState(): ConsoleState<Option<String>> = TODO()
}

data class PrintLine(val line: String) : Console<Unit>() {

    override fun toPar(): Par<Unit> = Par.lazyUnit { println(line) }

    override fun toThunk(): () -> Unit = { println(line) }

    override fun toState(): ConsoleState<Unit> = TODO()
}

//ConsoleState

class ForConsoleState private constructor() {
    companion object
}
typealias ConsoleStateOf<A> = arrow.Kind<ForConsoleState, A>

@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
inline fun <A> ConsoleStateOf<A>.fix(): ConsoleState<A> =
    this as ConsoleState<A>

data class Buffers(
    val input: List<String>,
    val output: List<String>
)

data class ConsoleState<A>(
    val run: (Buffers) -> Pair<A, Buffers>
) : ConsoleStateOf<A> {

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
}

package chapter13.sec4.console

import arrow.Kind
import arrow.higherkind
import chapter10.None
import chapter10.Option
import chapter10.Some
import chapter11.Monad
import chapter11.Par
import chapter13.boilerplate.free.Free
import chapter13.boilerplate.free.Suspend
import chapter13.sec4_3.ConsoleReader
import chapter13.sec4_3.ConsoleState

typealias ConsoleIO<A> = Free<ForConsole, A>

//tag::init1[]
@higherkind
sealed class Console<A> : ConsoleOf<A> {
    //tag::ignore[]
    abstract fun toReader(): ConsoleReader<A>
    abstract fun toState(): ConsoleState<A>

    //end::ignore[]
    abstract fun toPar(): Par<A> // <1>

    abstract fun toThunk(): () -> A // <2>

    //tag::init2[]
    companion object {
        fun stdin(): ConsoleIO<Option<String>> =
            Suspend(ReadLine)

        fun stdout(line: String): ConsoleIO<Unit> =
            Suspend(PrintLine(line))
    }

    //end::init2[]
    //tag::init5[]
    fun <B> flatMap(f: (A) -> Console<B>): Console<B> =
        when (this) {
            is ReadLine -> TODO("not possible!")
            is PrintLine -> TODO("also not possible!")
        }
    //end::init5[]
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

    //tag::ignore[]
    override fun toReader(): ConsoleReader<Option<String>> = TODO()
    override fun toState(): ConsoleState<Option<String>> = TODO()
    //end::ignore[]
}

data class PrintLine(val line: String) : Console<Unit>() {

    override fun toPar(): Par<Unit> = Par.lazyUnit { println(line) }

    override fun toThunk(): () -> Unit = { println(line) }

    //tag::ignore[]
    override fun toReader(): ConsoleReader<Unit> = TODO()
    override fun toState(): ConsoleState<Unit> = TODO()
    //end::ignore[]
}
//end::init1[]

object Listing1 {
    //tag::init3[]
    val f1: Free<ForConsole, Option<String>> =
        Console.stdout("I can only interact with the console")
            .flatMap { _ -> Console.stdin() }
    //end::init3[]
}

//tag::init4[]
fun <F, A> run(free: Free<F, A>, MF: Monad<F>): Kind<F, A>
//end::init4[]
    = TODO()

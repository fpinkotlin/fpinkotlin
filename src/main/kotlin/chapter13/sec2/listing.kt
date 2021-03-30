package chapter13.sec2

import chapter10.None
import chapter10.Option
import chapter10.Some
import chapter13.sec2.Listing12A.IO
import chapter13.sec2.Listing12A.stdout
import chapter13.sec2.Listing12E.converter

object Listing12A {

    data class Player(val name: String, val score: Int)

    fun winnerMsg(op: Option<Player>): String =
        when (op) {
            is Some -> "${op.get.name} is the winner"
            is None -> "It's a draw"
        }

    fun winner(p1: Player, p2: Player): Option<Player> =
        when {
            p1.score > p2.score -> Some(p1)
            p1.score < p2.score -> Some(p2)
            else -> None
        }

    //tag::init1[]
    interface IO {
        fun run(): Unit
    }

    fun stdout(msg: String): IO =
        object : IO {
            override fun run(): Unit = println(msg)
        }

    fun contest(p1: Player, p2: Player): IO =
        stdout(winnerMsg(winner(p1, p2)))
    //end::init1[]
}

object Listing12B {
    //tag::init2[]
    interface IO {
        companion object {
            fun empty(): IO = object : IO {
                override fun run(): Unit = Unit
            }
        }

        fun run(): Unit

        fun assoc(io: IO): IO = object : IO {
            override fun run() {
                this@IO.run() // <1>
                io.run() // <2>
            }
        }
    }
    //end::init2[]
}

object Listing12C {
    //tag::init3[]
    fun fahrenheitToCelsius(f: Double): Double = (f - 32) * 5.0 / 9.0

    fun converter() {
        println("Enter a temperature in Degrees Fahrenheit:")
        val d = readLine().orEmpty().toDouble()
        println(fahrenheitToCelsius(d))
    }
    //end::init3[]
}

object Listing12D {
    fun fahrenheitToCelsius(f: Double): Double = (f - 32) * 5.0 / 9.0

    //tag::init4[]
    fun converter(): IO {
        val prompt: IO =
            stdout("Enter a temperature in Degrees Fahrenheit:")
        TODO("now what??")
    }
    //end::init4[]
}

object Listing12E {
    //tag::init5[]
    interface IO<A> {

        //tag::companion[]
        companion object {

            fun <A> unit(a: () -> A) = object : IO<A> {
                override fun run(): A = a()
            }

            operator fun <A> invoke(a: () -> A) = unit(a)
        }

        //end::companion[]
        fun run(): A

        fun <B> map(f: (A) -> B): IO<B> =
            object : IO<B> {
                override fun run(): B = f(this@IO.run())
            }

        fun <B> flatMap(f: (A) -> IO<B>): IO<B> =
            object : IO<B> {
                override fun run(): B = f(this@IO.run()).run()
            }

        infix fun <B> assoc(io: IO<B>): IO<Pair<A, B>> =
            object : IO<Pair<A, B>> {
                override fun run(): Pair<A, B> =
                    this@IO.run() to io.run()
            }
    }
    //end::init5[]

    fun fahrenheitToCelsius(f: Double): Double = (f - 32) * 5.0 / 9.0

    //tag::init7[]
    fun stdin(): IO<String> = IO { readLine().orEmpty() }

    fun stdout(msg: String): IO<Unit> = IO { println(msg) }

    fun converter(): IO<Unit> =
        stdout("Enter a temperature in degrees Fahrenheit: ").flatMap {
            stdin().map { it.toDouble() }.flatMap { df ->
                stdout("Degrees Celsius: ${fahrenheitToCelsius(df)}")
            }
        }
    //end::init7[]

    //tag::init8[]
    val echo: IO<Unit> = stdin().flatMap(::stdout) // <1>

    val readInt: IO<Int> = stdin().map { it.toInt() } // <2>

    val readInts: IO<Pair<Int, Int>> = readInt assoc readInt // <3>
    //end::init8[]
}

fun main() {
    converter().run()
}

package chapter13.sec7

import arrow.higherkind
import chapter10.Monoid
import chapter10.None
import chapter10.Option
import chapter10.Some
import chapter13.boilerplate.free.Free
import chapter13.boilerplate.free.Return
import chapter13.boilerplate.free.Suspend

//tag::init1[]
@higherkind
interface Files<A> : FilesOf<A>

data class ReadLines(
    val file: String
) : Files<List<String>>

data class WriteLines(
    val file: String,
    val lines: List<String>
) : Files<Unit>
//end::init1[]

fun fahrenheitToCelsius(d: Double): Double = TODO()

//tag::init2[]
val p: Free<ForFiles, Unit> =
    Suspend(ReadLines("fahrenheit.txt"))
        .flatMap { lines: List<String> ->
            Suspend(WriteLines("celsius.txt", lines.map { s ->
                fahrenheitToCelsius(s.toDouble()).toString()
            }))
        }
//end::init2[]

//tag::init3[]
@higherkind
interface FilesH<A> : FilesHOf<A>

data class OpenRead(val file: String) : FilesH<HandleR>
data class OpenWrite(val file: String) : FilesH<HandleW>
data class ReadLine(val h: HandleR) : FilesH<Option<String>>
data class WriteLine(val h: HandleW) : FilesH<Unit>

interface HandleR
interface HandleW
//end::init3[]

fun handleW(f: () -> Unit): HandleW = TODO()

//tag::init4[]
fun loop(f: HandleR, c: HandleW): Free<ForFilesH, Unit> =
    Suspend(ReadLine(f)).flatMap { line: Option<String> ->
        when (line) {
            is None ->
                Return(Unit)
            is Some ->
                Suspend(WriteLine(handleW {
                    fahrenheitToCelsius(line.get.toDouble())
                })).flatMap { _ -> loop(f, c) }
        }
    }

fun convertFiles() =
    Suspend(OpenRead("fahrenheit.txt")).flatMap { f ->
        Suspend(OpenWrite("celsius.txt")).map { c ->
            loop(f, c)
        }
    }
//end::init4[]

val lines: List<String> = emptyList()

//tag::init5[]
fun movingAvg(n: Int, l: List<Double>): List<Double> = TODO()

val cs = movingAvg(
    5, lines.map { s ->
        fahrenheitToCelsius(s.toDouble())
    }).map { it.toString() }
//end::init5[]

//tag::init6[]
fun <A, B> windowed(
    n: Int,
    l: List<A>,
    f: (A) -> B,
    M: Monoid<B>
): List<B> = TODO()
//end::init6[]

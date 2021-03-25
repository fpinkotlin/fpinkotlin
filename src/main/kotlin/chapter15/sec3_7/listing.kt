package chapter15.sec3_7

import chapter13.boilerplate.io.ForIO
import chapter15.sec3_3.Process
import chapter15.sec3_5.fileW
import chapter15.sec3_5.lines
import chapter15.sec3_5.to

fun fahrenheitToCelsius(f: Double): Double = (f - 32) * 5.0 / 9.0

//tag::init1[]
val convertAll: Process<ForIO, Unit> =
    fileW("celsius.txt").take(1).flatMap { out ->
        lines("fahrenheit.txt")
            .flatMap { infile ->
                lines(infile)
                    .map { fahrenheitToCelsius(it.toDouble()) }
                    .flatMap { out(it.toString()) }
            }
    }.drain()
//end::init1[]

//tag::init2[]
val convertMultiSink: Process<ForIO, Unit> =
    lines("fahrenheit.txt")
        .flatMap { infile ->
            lines(infile)
                .map { fahrenheitToCelsius(it.toDouble()) }
                .map { it.toString() }
                .to(fileW("${infile}.celsius"))
        }.drain()
//end::init2[]

//tag::init3[]
val convertMultisink2: Process<ForIO, Unit> =
    lines("fahrenheit.txt").flatMap { infile ->
        lines(infile)
            .filter { !it.startsWith("#") }
            .map { fahrenheitToCelsius(it.toDouble()) }
            .filter { it > 0 } // <1>
            .map { it.toString() }
            .to(fileW("${infile}.celsius"))
    }.drain()
//end::init3[]

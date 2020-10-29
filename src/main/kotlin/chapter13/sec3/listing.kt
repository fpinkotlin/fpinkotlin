package chapter13.sec3

import chapter13.boilerplate.io.IO
import chapter13.boilerplate.io.fix
import chapter13.boilerplate.io.io.monad.monad

fun stdout(msg: String): IO<Unit> = IO { println(msg) }

//tag::init1[]
val p: IO<Unit> =
    IO.monad() // <1>
        .forever<Unit, Unit>(stdout("Still going...")) // <2>
        .fix() // <3>
//end::init1[]

interface IO<A> {
    fun run(): A

    //tag::init2[]
    fun <B> flatMap(f: (A) -> IO<B>): IO<B> =
        object : IO<B> {
            override fun run(): B = f(this@IO.run()).run()
        }
    //end::init2[]
}

fun main() {
    p.run()
}

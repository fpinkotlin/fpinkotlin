package chapter13.sec3

import chapter13.IO
import chapter13.fix
import chapter13.stdout

//tag::init1[]
val p: IO<Unit> =
    IO.forever<Unit, Unit>(
        stdout("Still going...")
    ).fix()
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

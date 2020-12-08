package chapter15.sec3

import arrow.Kind
import arrow.core.andThen
import arrow.higherkind
import chapter12.Either
import chapter15.sec3.Process.Companion.Await
import chapter15.sec3.Process.Companion.Halt

//tag::init1[]
@higherkind
sealed class Process<F, O> : ProcessOf<F, O> {
    companion object {
        data class Await<F, A, O>(
            val req: Kind<F, A>,
            val recv: (Either<Throwable, A>) -> Process<F, O> // <1>
        ) : Process<F, A>()

        data class Emit<F, O>(
            val head: O,
            val tail: Process<F, O>
        ) : Process<F, O>()

        data class Halt<F, O>(val err: Throwable) : Process<F, O>() // <2>

        object End : Exception() // <3>

        object Kill : Exception() // <4>
    }

    //tag::init2[]
    fun apply(p: () -> Process<F, O>): Process<F, O> =
        this.onHalt { ex: Throwable ->
            when (ex) {
                is End -> p() // <1>
                else -> Halt(ex) // <2>
            }
        }.fix()

    fun onHalt(f: (Throwable) -> ProcessOf<F, O>): ProcessOf<F, O> =
        when (this) {
            is Halt ->
                Try { f(this.err).fix() } // <3>
            is Emit ->
                Emit(this.head, tail.onHalt(f).fix())
            is Await<*, *, *> ->
                await<F, O, O>(req, recv) { it.onHalt(f).fix() } // <4>
        }

    //end::init2[]
    //tag::init5[]
    fun <O2> flatMap(f: (O) -> Process<F, O2>): Process<F, O2> =
        when (this) {
            is Halt ->
                Halt(err)
            is Emit ->
                Try { f(head) }.apply { tail.flatMap(f) }
            is Await<*, *, *> -> {
                await<F, O, O2>(
                    this.req,
                    this.recv
                ) { it.flatMap(f) }
            }
        }
    //end::init5[]
    //tag::ignore[]
    //TODO: implement `map` and `filter`
    //end::ignore[]
}

//end::init1[]
//tag::init3[]
fun <F, O> Try(p: () -> Process<F, O>): Process<F, O> =
    try {
        p()
    } catch (e: Throwable) {
        Halt(e)
    }
//end::init3[]

//tag::init4[]
fun <F, A, O> await(
    req: Kind<Any?, Any?>,
    recv: (Either<Throwable, Nothing>) -> Process<out Any?, out Any?>,
    fn: (Process<F, A>) -> Process<F, O>
): Process<F, O> =
    Await(
        req as Kind<F, Nothing>,
        recv as (Either<Throwable, A>) -> Process<F, A> andThen fn
    ).fix()
//end::init4[]

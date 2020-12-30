package chapter15.solutions.ex11

import arrow.Kind
import chapter12.Either
import chapter12.Left
import chapter12.Right
import chapter15.sec3.Process
import chapter15.sec3.Process.Companion.Await
import chapter15.sec3.Process.Companion.Emit
import chapter15.sec3.Process.Companion.End
import chapter15.sec3.Process.Companion.Halt
import chapter15.sec3.await
import chapter15.sec3.awaitAndThen

//tag::init[]
fun <F, A> eval(fa: Kind<F, A>): Process<F, A> =
    await<F, A, A>(fa) { ea: Either<Throwable, Nothing> ->
        when (ea) {
            is Right<A> -> Emit(ea.value, Halt(End))
            is Left -> Halt(ea.value)
        }
    }

fun <F, A, B> evalDrain(fa: Kind<F, A>): Process<F, B> =
    eval(fa).drain()

fun <F, A, B> Process<F, A>.drain(): Process<F, B> =
    when (this) {
        is Halt -> Halt(this.err)
        is Emit -> this.tail.drain()
        is Await<*, *, *> ->
            awaitAndThen<F, A, B>(
                this.req,
                { ei: Either<Throwable, Nothing> -> this.recv(ei) },
                { it.drain() }
            )
    }
//end::init[]

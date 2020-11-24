package chapter15.sec2

import chapter10.None
import chapter10.Option
import chapter10.Some
import chapter5.Cons
import chapter5.Empty
import chapter5.Stream

//tag::init1[]
sealed class Process<I, O> {
    //tag::init2[]
    operator fun invoke(s: Stream<I>): Stream<O> =
        when (this) {
            is Halt -> Stream.empty()
            is Await -> when (s) {
                is Cons -> this.recv(Some(s.head()))(s.tail())
                is Empty -> this.recv(None)(s)
            }
            is Emit -> Cons({ this.head }, { this.tail(s) })
        }
    //end::init2[]
    //process methods
}

data class Emit<I, O>(
    val head: O,
    val tail: Process<I, O> = Halt<I, O>() // <1>
) : Process<I, O>()

data class Await<I, O>(
    val recv: (Option<I>) -> Process<I, O>
) : Process<I, O>()

class Halt<I, O> : Process<I, O>()
//end::init1[]

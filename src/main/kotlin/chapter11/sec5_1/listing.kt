package chapter11.sec5_1

import arrow.Kind
import chapter11.sec2.Monad

//tag::init1[]
data class Id<A>(val a: A)
//end::init1[]

//tag::init2[]
data class State<S, out A>(val run: (S) -> Pair<A, S>) : IntStateOf<A> {

    companion object {
        fun <S, A> unit(a: A): State<S, A> =
            State { s: S -> a to s }
    }

    fun <B> map(f: (A) -> B): State<S, B> =
        flatMap { a: A -> unit<S, B>(f(a)) }

    fun <B> flatMap(f: (A) -> State<S, B>): State<S, B> =
        State { s1: S ->
            val (a: A, s2: S) = this.run(s1)
            f(a).run(s2)
        }
}
//end::init2[]

//tag::init3[]
typealias IntState<A> = State<Int, A>
//end::init3[]

sealed class ForIntState private constructor() {
    companion object
}

typealias IntStateOf<A> = Kind<ForIntState, A>

fun <A> IntStateOf<A>.fix() = this as IntState<A>

//tag::init4[]
val intState = object : Monad<ForIntState> { // <1>
    override fun <A> unit(a: A): IntStateOf<A> = // <2>
        IntState { s: Int -> a to s }

    override fun <A, B> flatMap(
        fa: IntStateOf<A>,
        f: (A) -> IntStateOf<B>
    ): IntStateOf<B> =
        fa.fix().flatMap { a: A -> f(a).fix() }
}
//end::init4[]

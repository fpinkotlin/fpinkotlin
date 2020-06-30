package chapter11

import arrow.Kind
import chapter8.RNG
import chapter8.State

data class Gen<A>(val sample: State<RNG, A>) : GenOf<A> {
    companion object {
        fun <A> unit(a: A): Gen<A> = Gen(State.unit(a))
    }

    fun <A, B> flatMap(f: (A) -> Gen<B>): Gen<B> = TODO()
}

class ForGen private constructor() {
    companion object
}

typealias GenOf<A> = Kind<ForGen, A>

fun <A> GenOf<A>.fix(): Gen<A> = this as Gen<A>


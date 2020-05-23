package chapter10

import arrow.Kind

class ForOption private constructor() {
    companion object
}

typealias OptionOf<A> = Kind<ForOption, A>

fun <A> OptionOf<A>.fix() = this as Option<A>

sealed class Option<out A> : OptionOf<A> {
    companion object {
        fun <A> empty(): Option<A> = None
        fun <A, B> flatMap(oa: Option<A>, f: (A) -> Option<B>): Option<B> =
            when (oa) {
                is None -> None
                is Some -> f(oa.get)
            }
    }
}

data class Some<out A>(val get: A) : Option<A>()
object None : Option<Nothing>()

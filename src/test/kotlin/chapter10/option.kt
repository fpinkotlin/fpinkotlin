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
    }
    fun <B> flatMap(f: (A) -> Option<B>): Option<B> =
        when (this) {
            is None -> None
            is Some -> f(this.get)
        }
}

data class Some<out A>(val get: A) : Option<A>()
object None : Option<Nothing>()

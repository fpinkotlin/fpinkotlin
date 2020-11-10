package chapter10

import arrow.higherkind

@higherkind
sealed class Option<out A> : OptionOf<A> {
    companion object {
        fun <A> empty(): Option<A> = None
        fun <A> of(a: A?): Option<A> = if (a == null) None else Some(a)
    }

    fun <B> flatMap(f: (A) -> Option<B>): Option<B> =
        when (this) {
            is None -> None
            is Some -> f(this.get)
        }
}

data class Some<out A>(val get: A) : Option<A>()
object None : Option<Nothing>()

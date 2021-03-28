package chapter4

sealed class Option<out A> {
    companion object {
        fun <A> empty(): Option<A> = None
    }

    fun <B> map(f: (A) -> B): Option<B> =
        when (this) {
            is None -> None
            is Some -> Some(f(this.get))
        }

    fun <B> flatMap(f: (A) -> Option<B>): Option<B> =
        this.map(f).getOrElse { None }

    fun isEmpty(): Boolean = this
        .map { false }
        .getOrElse { true }
}

fun <A> Option<A>.getOrElse(default: () -> A): A =
    when (this) {
        is None -> default()
        is Some -> this.get
    }

data class Some<out A>(val get: A) : Option<A>()

object None : Option<Nothing>()

package chapter13.boilerplate.free

import arrow.Kind
import arrow.higherkind

@higherkind
sealed class Free<F, A> : FreeOf<F, A> {
    companion object {
        fun <F, A> unit(a: A): Free<F, A> = TODO()
    }

    fun <B> flatMap(f: (A) -> Free<F, B>): Free<F, B> = TODO()

    fun <B> map(f: (A) -> B): Free<F, B> = TODO()
}

data class Return<F, A>(val a: A) : Free<F, A>()
data class Suspend<F, A>(val resume: Kind<F, A>) : Free<F, A>()
data class FlatMap<F, A, B>(
    val sub: Free<F, A>,
    val f: (A) -> Free<F, B>
) : Free<F, B>()

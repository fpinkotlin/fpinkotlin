package chapter13.sec4.free

import arrow.Kind
import arrow.higherkind
import chapter13.boilerplate.free.FreeOf

//tag::init[]
@higherkind
sealed class Free<F, A> : FreeOf<F, A> // <1>
data class Return<F, A>(val a: A) : Free<F, A>()
data class Suspend<F, A>(val s: Kind<F, A>) : Free<F, A>() // <2>
data class FlatMap<F, A, B>(
    val s: Free<F, A>,
    val f: (A) -> Free<F, B>
) : Free<F, B>()
//end::init[]

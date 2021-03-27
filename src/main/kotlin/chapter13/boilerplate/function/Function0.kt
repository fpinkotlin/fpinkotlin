package chapter13.boilerplate.function

import arrow.higherkind

@higherkind
data class Function0<out A>(internal val f: () -> A) : Function0Of<A>

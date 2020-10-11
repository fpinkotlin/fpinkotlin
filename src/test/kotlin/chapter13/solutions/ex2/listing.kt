package chapter13.solutions.ex2

import chapter13.FlatMap
import chapter13.Free
import chapter13.Return
import chapter13.Suspend
import chapter13.fix

operator fun <A> Function0Of<A>.invoke(): A = this.fix().f()

class ForFunction0 private constructor() {
    companion object
}
typealias Function0Of<A> = arrow.Kind<ForFunction0, A>

@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
inline fun <A> Function0Of<A>.fix(): Function0<A> = this as Function0<A>

data class Function0<out A>(internal val f: () -> A) : Function0Of<A>

//tag::init1[]
tailrec fun <A> runTrampoline(ffa: Free<ForFunction0, A>): A =
    when (ffa) {
        is Return -> ffa.a
        is Suspend -> ffa.s.invoke()
        is FlatMap<*, *, *> -> {
            val sout: Free<ForFunction0, A> =
                ffa.s.fix() as Free<ForFunction0, A>
            val fout = ffa.f as (A) -> Free<ForFunction0, A>
            when (sout) {
                is FlatMap<*, *, *> -> {
                    val sin = sout.s as Free<ForFunction0, A>
                    val fin = sout.f as (A) -> Free<ForFunction0, A>
                    runTrampoline(sin.flatMap { a: A -> fin(a).flatMap(fout) })
                }
                is Return -> sout.a
                is Suspend -> sout.s.invoke()
            }
        }
    }
//end::init1[]

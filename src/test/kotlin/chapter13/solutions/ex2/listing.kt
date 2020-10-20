package chapter13.solutions.ex2

import chapter13.boilerplate.free.FlatMap
import chapter13.boilerplate.free.Free
import chapter13.boilerplate.free.Return
import chapter13.boilerplate.free.Suspend
import chapter13.boilerplate.function.ForFunction0
import chapter13.boilerplate.function.fix

@Suppress("UNCHECKED_CAST")
//tag::init1[]
tailrec fun <A> runTrampoline(ffa: Free<ForFunction0, A>): A =
    when (ffa) {
        is Return -> ffa.a
        is Suspend -> ffa.resume.fix().f()
        is FlatMap<*, *, *> -> {
            val sout = ffa.sub as Free<ForFunction0, A>
            val fout = ffa.f as (A) -> Free<ForFunction0, A>
            when (sout) {
                is FlatMap<*, *, *> -> {
                    val sin = sout.sub as Free<ForFunction0, A>
                    val fin = sout.f as (A) -> Free<ForFunction0, A>
                    runTrampoline(sin.flatMap { a ->
                        fin(a).flatMap(fout)
                    })
                }
                is Return -> sout.a
                is Suspend -> sout.resume.fix().f()
            }
        }
    }
//end::init1[]

package chapter13.exercises.ex2

import chapter13.boilerplate.free.Free
import chapter13.boilerplate.function.ForFunction0

@Suppress("UNCHECKED_CAST")
//tag::init1[]
tailrec fun <A> runTrampoline(ffa: Free<ForFunction0, A>): A = TODO()
//end::init1[]

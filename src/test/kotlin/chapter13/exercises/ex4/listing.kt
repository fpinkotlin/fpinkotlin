package chapter13.exercises.ex4

import chapter13.boilerplate.free.Free
import chapter13.sec4.console.ForConsole
import chapter13.sec4_2.Translate

//tag::init1[]
fun <F, G, A> translate(
    free: Free<F, A>,
    translate: Translate<F, G>
): Free<G, A> = TODO()

fun <A> runConsole(a: Free<ForConsole, A>): A = TODO()
//end::init1[]

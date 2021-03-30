package chapter13.exercises.ex1

import chapter11.Monad
import chapter13.boilerplate.free.Free
import chapter13.boilerplate.free.FreePartialOf
import utils.SOLUTION_HERE

//tag::init1[]
fun <F, A, B> Free<F, A>.flatMap(f: (A) -> Free<F, B>): Free<F, B> =

    SOLUTION_HERE()

fun <F, A, B> Free<F, A>.map(f: (A) -> B): Free<F, B> =

    SOLUTION_HERE()
//end::init1[]

//tag::init2[]
fun <F> freeMonad(): Monad<FreePartialOf<F>> =

    SOLUTION_HERE()
//end::init2[]

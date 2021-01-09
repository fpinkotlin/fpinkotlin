package chapter13.exercises.ex1

import chapter11.Monad
import chapter13.boilerplate.free.Free
import chapter13.boilerplate.free.FreePartialOf

//tag::init1[]
fun <F, A, B> Free<F, A>.flatMap(f: (A) -> Free<F, B>): Free<F, B> = TODO()
fun <F, A, B> Free<F, A>.map(f: (A) -> B): Free<F, B> = TODO()
//end::init1[]

//tag::init2[]
fun <F> freeMonad(): Monad<FreePartialOf<F>> = TODO()
//end::init2[]

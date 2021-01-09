package chapter13.exercises.ex3

import arrow.Kind
import chapter13.Monad
import chapter13.boilerplate.free.Free

//tag::init1[]
tailrec fun <F, A> step(free: Free<F, A>): Free<F, A> = TODO()

fun <F, A> run(free: Free<F, A>, M: Monad<F>): Kind<F, A> = TODO()
//end::init1[]

package chapter13.exercises.ex3

import arrow.Kind
import chapter13.Monad
import chapter13.boilerplate.free.Free
import utils.SOLUTION_HERE

//tag::init1[]
tailrec fun <F, A> step(free: Free<F, A>): Free<F, A> =

    SOLUTION_HERE()

fun <F, A> run(free: Free<F, A>, M: Monad<F>): Kind<F, A> =

    SOLUTION_HERE()
//end::init1[]

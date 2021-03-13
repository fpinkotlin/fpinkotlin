package chapter13.solutions.ex3

import arrow.Kind
import chapter13.Monad
import chapter13.boilerplate.free.FlatMap
import chapter13.boilerplate.free.Free
import chapter13.boilerplate.free.Return
import chapter13.boilerplate.free.Suspend

//tag::init1[]
@Suppress("UNCHECKED_CAST")
tailrec fun <F, A> step(free: Free<F, A>): Free<F, A> =
    when (free) {
        is FlatMap<*, *, *> -> {
            val y = free.sub as Free<F, A>
            val g = free.f as (A) -> Free<F, A>
            when (y) {
                is FlatMap<*, *, *> -> {
                    val x = y.sub as Free<F, A>
                    val f = y.f as (A) -> Free<F, A>
                    step(x.flatMap { a -> f(a).flatMap(g) })
                }
                is Return -> step(g(y.a))
                else -> free
            }
        }
        else -> free
    }

@Suppress("UNCHECKED_CAST")
fun <F, A> run(free: Free<F, A>, M: Monad<F>): Kind<F, A> =
    when (val stepped = step(free)) {
        is Return -> M.unit(stepped.a)
        is Suspend -> stepped.resume
        is FlatMap<*, *, *> -> {
            val x = stepped.sub as Free<F, A>
            val f = stepped.f as (A) -> Free<F, A>
            when (x) {
                is Suspend<F, A> ->
                    M.flatMap(x.resume) { a: A -> run(f(a), M) }
                else -> throw RuntimeException(
                    "Impossible, step eliminates such cases"
                )
            }
        }
    }
//end::init1[]

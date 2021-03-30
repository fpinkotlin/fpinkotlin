package chapter7.exercises.ex4

import utils.SOLUTION_HERE
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

typealias Par<A> = (ExecutorService) -> Future<A>

object Pars {

    //tag::init[]
    fun <A, B> asyncF(f: (A) -> B): (A) -> Par<B> =

        SOLUTION_HERE()
    //end::init[]

    fun <A> unit(a: () -> A): Par<A> =
        { es: ExecutorService -> TODO() }

    fun <A> fork(
        a: () -> Par<A>
    ): Par<A> =
        { es: ExecutorService ->
            es.submit(Callable<A> { a()(es).get() })
        }

    fun <A> lazyUnit(a: () -> A): Par<A> =
        fork { unit { a() } }

    fun <A> run(a: Par<A>): A = SOLUTION_HERE()
}

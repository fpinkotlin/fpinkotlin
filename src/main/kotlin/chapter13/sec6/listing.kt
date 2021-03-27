package chapter13.sec6

import arrow.Kind
import chapter13.Monad
import chapter13.boilerplate.free.Free
import chapter13.boilerplate.par.ForPar
import chapter13.boilerplate.par.Par
import chapter13.boilerplate.par.fix
import chapter13.boilerplate.par.par.monad.monad
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

//tag::init1[]
typealias IO<A> = Free<ForPar, A>
//end::init1[]

fun <A> run(io: IO<A>): A = TODO()

fun <F, A> run(free: Free<F, A>, monad: Monad<F>): Kind<F, A> = TODO()

//tag::init2[]
abstract class App {

    fun main(args: Array<String>) { // <1>
        val pool = Executors.newFixedThreadPool(8)
        unsafePerformIO(pureMain(args), pool)
    }

    private fun <A> unsafePerformIO(
        ioa: IO<A>,
        pool: ExecutorService
    ): A =
        run(ioa, Par.monad()).fix().run(pool).get() // <2>

    abstract fun pureMain(args: Array<String>): IO<Unit> // <3>
}
//end::init2[]

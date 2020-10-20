package chapter13.sec5

import arrow.Kind
import chapter11.ForPar
import chapter11.fix
import chapter13.Monad
import chapter13.boilerplate.free.Free

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

//tag::init1[]
typealias IO<A> = Free<ForPar, A>
//end::init1[]

fun <A> run(io: IO<A>): A = TODO()

fun <F, A> run(free: Free<F, A>, M: Monad<F>): Kind<F, A> = TODO()

fun parMonad(): Monad<ForPar> = TODO()

//tag::init2[]
abstract class App {

    private fun <A> unsafePerformIO(
        ioa: IO<A>,
        pool: ExecutorService
    ): A =
        run(ioa, parMonad()).fix().run(pool).get() // <1>

    fun main(args: Array<String>): Unit { // <2>
        val pool = Executors.newFixedThreadPool(8)
        unsafePerformIO(pureMain(args), pool)
    }

    abstract fun pureMain(args: Array<String>): IO<Unit> // <3>
}
//end::init2[]


/*
abstract class App {
  import java.util.concurrent._

  def unsafePerformIO[A](a: IO[A])(pool: ExecutorService): A =      <1>
    Par.run(pool)(run(a)(parMonad))

  def main(args: Array[String]): Unit = {                            <2>
    val pool = Executors.fixedThreadPool(8)
    unsafePerformIO(pureMain(args))(pool)
  }

  def pureMain(args: IndexedSeq[String]): IO[Unit]                   <3>
}

 */
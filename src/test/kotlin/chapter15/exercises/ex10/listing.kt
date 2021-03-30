package chapter15.exercises.ex10

import arrow.Kind
import chapter12.Either
import chapter13.Monad
import chapter15.sec3.Process
import utils.SOLUTION_HERE

// import chapter15.sec3.Process.Companion.Await
// import chapter15.sec3.Process.Companion.Emit
// import chapter15.sec3.Process.Companion.End
// import chapter15.sec3.Process.Companion.Halt
// import chapter15.sec3.tryP

//tag::init[]
fun <F, O> Process<F, O>.runLog(
    MC: MonadCatch<F>
): Kind<F, Sequence<O>> =

    SOLUTION_HERE()

interface MonadCatch<F> : Monad<F> {
    fun <A> attempt(a: Kind<F, A>): Kind<F, Either<Throwable, A>>
    fun <A> fail(t: Throwable): Kind<F, A>
}
//end::init[]

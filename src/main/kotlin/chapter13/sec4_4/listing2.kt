package chapter13.sec4_4

import arrow.higherkind
import chapter12.Either
import chapter13.boilerplate.free.Free
import chapter13.boilerplate.free.Suspend
import java.util.concurrent.ExecutorService

//tag::init1[]
abstract class Future<A> {
    internal abstract fun invoke(cb: (A) -> Unit)
}

@higherkind
class Par<A>(val run: (ExecutorService) -> Future<A>) : ParOf<A>
//end::init1[]

//tag::init2[]
fun <A> async(run: ((A) -> Unit) -> Unit): Par<A> =
    Par { es: ExecutorService ->
        object : Future<A>() {
            override fun invoke(cb: (A) -> Unit): Unit = run(cb)
        }
    }
//end::init2[]

//tag::init3[]
fun nonblockingRead(
    source: Source,
    numBytes: Int
): Par<Either<Throwable, Array<Byte>>> =
    async { cb: (Either<Throwable, Array<Byte>>) -> Unit ->
        source.readBytes(numBytes, cb)
    }

fun readPar(
    source: Source,
    numBytes: Int
): Free<ForPar, Either<Throwable, Array<Byte>>> =
    Suspend(nonblockingRead(source, numBytes))
//end::init3[]

//tag::init4[]
val src: Source = TODO("define the source")
val prog: Free<ForPar, Unit> =
    readPar(src, 1024).flatMap { chunk1 ->
        readPar(src, 1024).map { chunk2 ->
            //do something with chunks
        }
    }
//end::init4[]

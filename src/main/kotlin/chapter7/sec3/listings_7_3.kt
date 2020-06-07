package chapter7.sec3

import chapter7.sec3.Pars.map2
import chapter7.sec3.Pars.unit
import chapter7.sec4.fork
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

typealias Par<A> = (ExecutorService) -> Future<A>

//tag::init1[]
object Pars {
    fun <A> unit(a: A): Par<A> =
        { es: ExecutorService -> UnitFuture(a) } // <1>

    data class UnitFuture<A>(val a: A) : Future<A> {

        override fun get(): A = a

        override fun get(timeout: Long, timeUnit: TimeUnit): A = a

        override fun cancel(evenIfRunning: Boolean): Boolean = false

        override fun isDone(): Boolean = true

        override fun isCancelled(): Boolean = false
    }

    fun <A, B, C> map2(
        a: Par<A>,
        b: Par<B>,
        f: (A, B) -> C
    ): Par<C> = // <2>
        { es: ExecutorService ->
            val af: Future<A> = a(es)
            val bf: Future<B> = b(es)
            UnitFuture(f(af.get(), bf.get())) // <3>
        }

    fun <A> fork(
        a: () -> Par<A>
    ): Par<A> = // <4>
        { es: ExecutorService ->
            es.submit(Callable<A> { a()(es).get() })
        }
}
//end::init1[]

val step1 = {
    //tag::init2[]
    fun sortPar(parList: Par<List<Int>>): Par<List<Int>> = TODO()
    //end::init2[]
}

val step2 = {
    //tag::init3[]
    fun sortPar(parList: Par<List<Int>>): Par<List<Int>> =
        map2(parList, unit(Unit)) { a, _ -> a.sorted() }
    //end::init3[]
}

val step3 = {
    //tag::init4[]
    fun <A, B> map(pa: Par<A>, f: (A) -> B): Par<B> =
        map2(pa, unit(Unit), { a, _ -> f(a) })
    //end::init4[]
}

val step4 = {
    fun <A, B> map(pa: Par<A>, f: (A) -> B): Par<B> = TODO()
    //tag::init5[]
    fun sortPar(parList: Par<List<Int>>): Par<List<Int>> =
        map(parList) { it.sorted() }
    //end::init5[]
}

val step5 = {
    //tag::init6[]
    fun <A, B> parMap(
        ps: List<A>,
        f: (A) -> B
    ): Par<List<B>> = TODO()
    //end::init6[]
}

val step6 = {
    fun <A, B> asyncF(f: (A) -> B): (A) -> Par<B> = TODO()
    //tag::init7[]
    fun <A, B> parMap(
        ps: List<A>,
        f: (A) -> B
    ): Par<List<B>> {
        val fbs: List<Par<B>> = ps.map(asyncF(f))
        TODO()
    }
    //end::init7[]
}

val step7 = {
    fun <A, B> asyncF(f: (A) -> B): (A) -> Par<B> = TODO()
    fun <A> sequence(ps: List<Par<A>>): Par<List<A>> = TODO()
    //tag::init8[]
    fun <A, B> parMap(
        ps: List<A>,
        f: (A) -> B
    ): Par<List<B>> = fork {
        val fbs: List<Par<B>> = ps.map(asyncF(f))
        sequence(fbs)
    }
    //end::init8[]
}

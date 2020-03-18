package chapter7.sec4

import chapter7.sec3.Pars
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

typealias Par<A> = (ExecutorService) -> Future<A>

fun <A, B> map(pa: Par<A>, f: (A) -> B): Par<B> =
    Pars.map2(pa, Pars.unit(Unit), { a, _ -> f(a) })

fun <A> unit(a: A): Par<A> =
    { es: ExecutorService -> Pars.UnitFuture(a) }

fun <A> lazyUnit(a: () -> A): Par<A> = TODO()

val step1 = {
    //tag::init1[]
    map(unit(1)) { it + 1 } == unit(2)
    //end::init1[]
}

val step2 = {
    //tag::init2[]
    fun <A> equal(es: ExecutorService, p1: Par<A>, p2: Par<A>): Boolean =
        p1(es).get() == p2(es).get()
    //end::init2[]
}

val step3 = {
    val x = 1
    val f = { a: Int -> a + 1 }
    //tag::init3[]
    map(unit(x), f) == unit(f(x))
    //end::init3[]
}

val step4 = {
    //tag::init4[]
    val x = 1
    val y = unit(x)
    val f = { a: Int -> a + 1 }
    val id = { a: Int -> a }

    map(unit(x), f) == unit(f(x)) // <1>
    map(unit(x), id) == unit(id(x)) // <2>
    map(unit(x), id) == unit(x) // <3>
    map(y, id) == y // <4>
    //end::init4[]
}

//tag::init7[]
fun <A> fork(a: () -> Par<A>): Par<A> =
    { es ->
        es.submit(Callable<A> {
            a()(es).get() // <1>
        })
    }
//end::init7[]

val step5 = {
    val x = unit(1)
    //tag::init5[]
    fork { x } == x
    //end::init5[]
}

val step6 = {
    fun <A> equal(es: ExecutorService, p1: Par<A>, p2: Par<A>): Boolean =
        p1(es).get() == p2(es).get()

    //tag::init6[]
    val a = lazyUnit { 42 + 1 }
    val es = Executors.newFixedThreadPool(1)
    equal(es, a, fork { a })
    //end::init6[]
}

val step7 = {
    //tag::init8[]
    fun <A> fork(pa: () -> Par<A>): Par<A> =
        { es -> pa()(es) }
    //end::init8[]
}

val step8 = {
    //tag::init9[]
    fun <A> delay(pa: () -> Par<A>): Par<A> =
        { es -> pa()(es) }
    //end::init9[]
}

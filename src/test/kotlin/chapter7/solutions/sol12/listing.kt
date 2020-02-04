package chapter7.solutions.sol12

import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

typealias Par<A> = (ExecutorService) -> Future<A>

//tag::init1[]
fun <A> join(a: Par<Par<A>>): Par<A> =
    { es: ExecutorService ->
        a(es).get()(es)
    }
//end::init1[]

//tag::init2[]
fun <A, B> flatMap(pa: Par<A>, f: (A) -> Par<B>): Par<B> =
    { es: ExecutorService ->
        f(pa(es).get())(es)
    }
//end::init2[]

//tag::init3[]
fun <A> join2(a: Par<Par<A>>): Par<A> =
    { es: ExecutorService ->
        a(es).get()(es)
    }
//end::init3[]


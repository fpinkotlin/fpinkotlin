package chapter7.solutions.ex13

import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

typealias Par<A> = (ExecutorService) -> Future<A>

//tag::init1[]
fun <A> join(a: Par<Par<A>>): Par<A> =
    { es: ExecutorService -> a(es).get()(es) }
//end::init1[]

fun <A, B> map(pa: Par<A>, f: (A) -> B): Par<B> = TODO()

fun <A, B> flatMap(pa: Par<A>, f: (A) -> Par<B>): Par<B> = TODO()

//tag::init2[]
fun <A, B> flatMapViaJoin(pa: Par<A>, f: (A) -> Par<B>): Par<B> =
    join(map(pa, f))
//end::init2[]

//tag::init3[]
fun <A> joinViaFlatMap(a: Par<Par<A>>): Par<A> =
    flatMap(a, { it })
//end::init3[]


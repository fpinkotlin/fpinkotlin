package chapter7.solutions.ex10

import chapter7.sec3.Pars
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

typealias Par<A> = (ExecutorService) -> Future<A>

//tag::init1[]
fun <A> choiceN(n: Par<Int>, choices: List<Par<A>>): Par<A> =
    { es: ExecutorService ->
        choices[n(es).get()].invoke(es)
    }
//end::init1[]

fun <A> unit(a: A): Par<A> =
    { es: ExecutorService -> Pars.UnitFuture(a) }

fun <A, B> map(pa: Par<A>, f: (A) -> B): Par<B> =
    Pars.map2(pa, Pars.unit(Unit), { a, _ -> f(a) })

//tag::init2[]
fun <A> choice(cond: Par<Boolean>, t: Par<A>, f: Par<A>): Par<A> =
    { es: ExecutorService ->
        choiceN(
            map(cond, { if (it) 1 else 0 }),
            listOf(f, t)
        )(es)
    }
//end::init2[]

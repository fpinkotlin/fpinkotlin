package chapter7.sec5

import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

typealias Par<A> = (ExecutorService) -> Future<A>

fun <A> run(es: ExecutorService, a: Par<A>): Future<A> = a(es)

val listing1 = {
    //tag::init1[]
    fun <A> choice(cond: Par<Boolean>, t: Par<A>, f: Par<A>): Par<A>
    //end::init1[]
        = TODO()
}

val listing2 = {
    //tag::init2[]
    fun <A> choice(cond: Par<Boolean>, t: Par<A>, f: Par<A>): Par<A> =
        { es: ExecutorService ->
            when (run(es, cond).get()) { // <1>
                true -> run(es, t)
                false -> run(es, f)
            }
        }
    //end::init2[]
}

val listing3 = {
    //tag::init3[]
    fun <A> choiceN(n: Par<Int>, choices: List<Par<A>>): Par<A>
    //end::init3[]
        = TODO()
}

val listing4 = {
    //tag::init4[]
    fun <A, B> flatMap(pa: Par<A>, f: (A) -> Par<B>): Par<B>
    //end::init4[]
        = TODO()
}

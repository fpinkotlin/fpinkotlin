package chapter7.exercises.ex13

import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

typealias Par<A> = (ExecutorService) -> Future<A>

//tag::init[]
fun <A> join(a: Par<Par<A>>): Par<A> = TODO()
//end::init[]

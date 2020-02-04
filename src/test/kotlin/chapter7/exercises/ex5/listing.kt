package chapter7.exercises.ex5

import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

typealias Par<A> = (ExecutorService) -> Future<A>

//tag::init1[]
fun <A> sequence(ps: List<Par<A>>): List<Par<A>> = TODO()
//end::init1[]

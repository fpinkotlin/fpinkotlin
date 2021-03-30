package chapter7.exercises.ex13

import utils.SOLUTION_HERE
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

typealias Par<A> = (ExecutorService) -> Future<A>

//tag::init[]
fun <A> join(a: Par<Par<A>>): Par<A> =

    SOLUTION_HERE()
//end::init[]

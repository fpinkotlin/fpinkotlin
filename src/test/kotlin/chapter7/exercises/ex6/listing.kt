package chapter7.exercises.ex6

import utils.SOLUTION_HERE
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

typealias Par<A> = (ExecutorService) -> Future<A>

//tag::init[]
fun <A> parFilter(
    sa: List<A>,
    f: (A) -> Boolean
): Par<List<A>> =

    SOLUTION_HERE()
//end::init[]

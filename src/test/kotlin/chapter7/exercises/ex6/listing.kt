package chapter7.exercises.ex6

import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

typealias Par<A> = (ExecutorService) -> Future<A>

object Listing {
    //tag::init[]
    fun <A> parFilter(
        sa: List<A>,
        f: (A) -> Boolean
    ): Par<List<A>> = TODO()
//end::init[]
}

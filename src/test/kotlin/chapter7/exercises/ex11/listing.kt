package chapter7.exercises.ex11

import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

typealias Par<A> = (ExecutorService) -> Future<A>

//tag::init[]
fun <A, B> chooser(pa: Par<A>, choices: (A) -> Par<B>): Par<B> = TODO()
//end::init[]

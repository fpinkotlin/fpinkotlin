package chapter7.exercises.ex5

// import chapter7.sec1.splitAt
// import chapter7.solutions.ex3.TimedMap2Future
// import java.util.concurrent.Callable
import utils.SOLUTION_HERE
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

typealias Par<A> = (ExecutorService) -> Future<A>

//tag::init1[]
fun <A> sequence(ps: List<Par<A>>): Par<List<A>> =

    SOLUTION_HERE()
//end::init1[]

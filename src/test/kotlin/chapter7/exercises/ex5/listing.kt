package chapter7.exercises.ex5

// import chapter7.sec1.splitAt
// import chapter7.solutions.sol3.TimedMap2Future
// import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

typealias Par<A> = (ExecutorService) -> Future<A>

object Listing {
    //tag::init1[]
    fun <A> sequence(ps: List<Par<A>>): Par<List<A>> = TODO()
    //end::init1[]
}

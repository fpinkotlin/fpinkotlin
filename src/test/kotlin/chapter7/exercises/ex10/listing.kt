package chapter7.exercises.ex10

// import chapter7.sec3.Pars
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future


typealias Par<A> = (ExecutorService) -> Future<A>

fun <A> choiceN(n: Par<Int>, choices: List<Par<A>>): Par<A> = TODO()

fun <A> choice(cond: Par<Boolean>, t: Par<A>, f: Par<A>): Par<A> = TODO()

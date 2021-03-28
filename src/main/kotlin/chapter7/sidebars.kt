package chapter7

interface Callable<A> {
    fun call(): A
}

//tag::sb1[]
interface Runnable {
    fun run(): Unit
}

class Thread(r: Runnable) {
    fun start(): Unit = TODO() // <1>
    fun join(): Unit = TODO() // <2>
}
//end::sb1[]

//tag::sb2[]
class ExecutorService {
    fun <A> submit(a: Callable<A>): Future<A> = TODO()
}

interface Future<A> {
    fun get(): A
}
//end::sb2[]

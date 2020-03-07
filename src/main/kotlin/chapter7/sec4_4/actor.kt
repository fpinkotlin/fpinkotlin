package chapter7.sec4_4

import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference

class Actor<A>(
    val strategy: Strategy,
    val handler: (A) -> Unit
) {
    private val onError: (Throwable) -> Unit = { throw it }
    private val tail = AtomicReference(Node<A>())
    private val head = AtomicReference(tail.get())
    private val suspended = AtomicInteger(1)

    fun send(a: A) {
        val n = Node(a)
        head.getAndSet(n).lazySet(n)
        trySchedule()
    }

    fun <B> contramap(f: (B) -> A): Actor<B> =
        Actor(strategy) { b: B -> this.send(f(b)) }

    private fun trySchedule() {
        if (suspended.compareAndSet(1, 0)) schedule()
    }

    private fun schedule() = strategy { act() }

    private fun act() {
        val t = tail.get()
        val n = batchHandle(t, 1024)
        if (n != t) {
            n.a = null
            tail.lazySet(n)
            schedule()
        } else {
            suspended.set(1)
            if (n.get() != null) trySchedule()
        }
    }

    private tailrec fun batchHandle(t: Node<A>, i: Int): Node<A> {
        val n = t.get()
        return if (n != null) {
            try {
                handler(n.a!!)
            } catch (ex: Throwable) {
                onError(ex)
            }
            if (i > 0) batchHandle(n, i - 1) else n
        } else t
    }
}

class Node<A>(var a: A? = null) : AtomicReference<Node<A>>()

interface Strategy {

    operator fun <A> invoke(a: () -> A): (Unit) -> A

    companion object {

        fun from(es: ExecutorService): Strategy =
            object : Strategy {
                override fun <A> invoke(a: () -> A): (Unit) -> A {
                    val fa = es.submit(Callable<A> { a() })
                    return { fa.get() }
                }
            }

        fun sequential(): Strategy = object :
            Strategy {
            override fun <A> invoke(a: () -> A): (Unit) -> A = { a() }
        }
    }
}

fun main() {
    //tag::client1[]
    val es: ExecutorService = Executors.newFixedThreadPool(4) // <1>
    val s = Strategy.from(es) // <2>
    val echoer = Actor<String>(s) { // <3>
        println("got message: $it")
    }
    //end::client1[]

    //tag::client2[]
    echoer.send("hello") // <1>
    //got message: hello // <2>

    echoer.send("goodbye") // <3>
    //got message: goodbye

    echoer.send("You're just repeating everything I say, aren't you?")
    //got message: You're just repeating everything I say, aren't you?
    //end::client2[]
}

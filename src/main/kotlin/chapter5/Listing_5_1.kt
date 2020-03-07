package chapter5

import chapter3.List
import kotlin.system.exitProcess

object Listing_5_1 {

    fun <A, B> List<A>.map(f: (A) -> B): List<B> = TODO()
    fun <A> List<A>.filter(f: (A) -> Boolean): List<A> = TODO()

    val listing = {
        //tag::init[]
        List.of(1, 2, 3, 4)
            .map { it + 10 }.filter { it % 2 == 0 }.map { it * 3 }
        List.of(11, 12, 13, 14)
            .filter { it % 2 == 0 }.map { it * 3 }
        List.of(12, 14)
            .map { it * 3 }
        List.of(36, 42)
        //end::init[]
    }

    //tag::init2[]
    fun square(x: Double): Double = x * x
    //end::init2[]

    val input = ""
    //tag::init3[]
    val result =
        if (input.isEmpty()) exitProcess(-1) else input
    //end::init3[]

    val a = 10
    //tag::init4[]
    fun <A> if2(
        cond: Boolean,
        onTrue: () -> A, //<1>
        onFalse: () -> A
    ): A = if (cond) onTrue() else onFalse()

    val y = if2((a < 22),
        { println("a") }, // <2>
        { println("b") }
    )
    //end::init4[]

    //tag::maybetwice1[]
    fun maybeTwice1(b: Boolean, i: () -> Int) =
        if (b) i() + i() else 0
    //end::maybetwice1[]

    //tag::maybetwice2[]
    fun maybeTwice2(b: Boolean, i: () -> Int) {
        val j: Int by lazy(i)
        if (b) j + j else 0
    }
    //end::maybetwice2[]
}

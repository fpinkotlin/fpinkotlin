package chapter11.exercises.ex10

import arrow.core.ForOption
import chapter11.Monad

interface Listing<A> : Monad<ForOption> {

    val v: A

    fun exercise() {
    }
}

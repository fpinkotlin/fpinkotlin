package chapter13

import arrow.Kind
import chapter12.Functor
import chapter13.boilerplate.io.IO
import chapter5.Stream

interface Monad<F> : Functor<F> {

    fun <A> unit(a: A): Kind<F, A>

    fun <A, B> flatMap(fa: Kind<F, A>, f: (A) -> Kind<F, B>): Kind<F, B>
}

fun stdin(): IO<String> = IO { readLine().orEmpty() }

fun stdout(msg: String): IO<Unit> = IO { println(msg) }

fun IntRange.toStream(): Stream<Int> {
    fun stream(from: Int, to: Int): Stream<Int> =
        when (from) {
            this.last + 1 ->
                Stream.empty()
            else ->
                Stream.cons({ from }, { stream(from + 1, to) })
        }
    return stream(this.first, this.last)
}

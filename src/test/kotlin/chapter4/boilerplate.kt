package chapter4

import chapter3.Cons
import chapter3.List
import chapter3.Nil

fun <A, B> List<A>.map(f: (A) -> B): List<B> =
    this.foldRight(
        List.empty(),
        { a, b -> Cons(f(a), b) })

fun <A, B> List<A>.foldRight(z: B, f: (A, B) -> B): B =
    when (this) {
        is Nil -> z
        is Cons -> f(this.head, this.tail.foldRight(z, f))
    }

fun List<Double>.sum(): Double =
    this.foldRight(0.0, { a, b -> a + b })

fun <A> List<A>.size(): Int =
    this.foldRight(0, { _, acc -> 1 + acc })

fun List<Double>.isEmpty(): Boolean = when (this) {
    is Nil -> true
    is Cons -> false
}

fun <A, B, C> map2(
    oa: Option<A>,
    ob: Option<B>,
    f: (A, B) -> C
): Option<C> =
    oa.flatMap { a ->
        ob.map { b ->
            f(a, b)
        }
    }

fun <E, A, B> Either<E, A>.map(f: (A) -> B): Either<E, B> =
    when (this) {
        is Left -> this
        is Right -> Right(f(this.value))
    }

fun <E, A> Either<E, A>.orElse(f: () -> Either<E, A>): Either<E, A> =
    when (this) {
        is Left -> f()
        is Right -> this
    }

fun <E, A, B> Either<E, A>.flatMap(f: (A) -> Either<E, B>): Either<E, B> =
    when (this) {
        is Left -> this
        is Right -> f(this.value)
    }

fun <E, A, B, C> map2(
    ae: Either<E, A>,
    be: Either<E, B>,
    f: (A, B) -> C
): Either<E, C> =
    ae.flatMap { a -> be.map { b -> f(a, b) } }

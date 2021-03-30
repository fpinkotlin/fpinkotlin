package chapter11.solutions.ex18

import chapter11.sec5_2.State

fun <S, A> unit(a: A): State<S, A> =
    State { s: S -> a to s }

fun <S> getState(): State<S, S> =
    State { s -> s to s }

fun <S> setState(s: S): State<S, Unit> =
    State { Unit to s }

fun main() {
    //tag::init[]
    getState<Int>().flatMap { a -> setState(a) } == unit<Int, Unit>(Unit)

    setState<Int>(1).flatMap { _ -> getState<Int>() } == unit<Int, Int>(1)
    //end::init[]
}

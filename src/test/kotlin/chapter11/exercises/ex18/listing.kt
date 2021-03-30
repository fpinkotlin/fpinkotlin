package chapter11.exercises.ex18

import chapter11.sec5_2.State

fun <S, A> unit(a: A): State<S, A> =
    State { s: S -> a to s }

fun <S> getState(): State<S, S> =
    State { s -> s to s }

fun <S> setState(s: S): State<S, Unit> =
    State { Unit to s }

fun main() {
    //tag::init[]
    TODO("Express laws in terms of flatMap, unit, getState and setState")
    //end::init[]
}

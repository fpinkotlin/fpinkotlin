package chapter11.exercises.ex1

import arrow.core.ForListK
import arrow.core.ForSequenceK
import chapter10.ForList
import chapter10.ForOption
import chapter11.ForPar
import chapter11.sec2.Monad

//tag::init1[]
object Monads {

    val parMonad: Monad<ForPar> = TODO()

    val optionMonad: Monad<ForOption> = TODO()

    val listMonad: Monad<ForList> = TODO()

    val listKMonad: Monad<ForListK> = TODO()

    val sequenceKMonad: Monad<ForSequenceK> = TODO()
}
//end::init1[]

package chapter11.exercises.ex1

import arrow.core.ForListK
import arrow.core.ForSequenceK
import chapter10.ForList
import chapter10.ForOption
import chapter11.ForPar
import chapter11.sec2.Monad
import utils.SOLUTION_HERE

//tag::init1[]
object Monads {

    fun parMonad(): Monad<ForPar> =

        SOLUTION_HERE()

    fun optionMonad(): Monad<ForOption> =

        SOLUTION_HERE()

    fun listMonad(): Monad<ForList> =

        SOLUTION_HERE()

    fun listKMonad(): Monad<ForListK> =

        SOLUTION_HERE()

    fun sequenceKMonad(): Monad<ForSequenceK> =

        SOLUTION_HERE()
}
//end::init1[]

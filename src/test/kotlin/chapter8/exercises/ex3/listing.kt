package chapter8.exercises.ex3

import utils.SOLUTION_HERE

//tag::init[]
interface Prop {
    fun check(): Boolean
    fun and(p: Prop): Prop =

        SOLUTION_HERE()
}
//end::init[]

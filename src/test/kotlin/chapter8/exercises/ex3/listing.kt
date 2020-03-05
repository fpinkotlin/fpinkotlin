package chapter8.exercises.ex3

//tag::init[]
interface Prop {
    fun check(): Boolean
    fun and(p: Prop): Prop = TODO()
}
//end::init[]

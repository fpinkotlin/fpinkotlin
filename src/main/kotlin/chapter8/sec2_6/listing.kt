package chapter8.sec2_6

//tag::init[]
interface Prop {
    fun check(): Unit
    fun and(p: Prop): Prop
}
//end::init[]

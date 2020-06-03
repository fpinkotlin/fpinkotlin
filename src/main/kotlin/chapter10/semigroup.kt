package chapter10

//tag::init1[]
interface Semigroup<A> {
    fun op(a1: A, a2: A): A
}
//end::init1[]

//tag::init2[]
interface Monoid<A> : Semigroup<A> {
    val zero: A
}
//end::init2[]
package chapter10

//tag::init1[]
interface Semigroup<A> {
    fun combine(a1: A, a2: A): A
}
//end::init1[]

//tag::init2[]
interface Monoid<A> : Semigroup<A> {
    val nil: A
}
//end::init2[]

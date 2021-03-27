package chapter10.sec2

import arrow.core.extensions.list.foldable.foldLeft
import chapter10.sec1.Monoid

val listing1 = {
    //tag::init1[]
    fun <A, B> foldRight(z: B, f: (A, B) -> B): B
    //end::init1[]
        = TODO()

    //tag::init2[]
    fun <A, B> foldLeft(z: B, f: (B, A) -> B): B
    //end::init2[]
        = TODO()
}

val listing2 = {
    //tag::init3[]
    fun <A> foldRight(z: A, f: (A, A) -> A): A
    //end::init3[]
        = TODO()

    //tag::init4[]
    fun <A> foldLeft(z: A, f: (A, A) -> A): A
    //end::init4[]
        = TODO()
}

//tag::init5[]
fun <A> concatenate(la: List<A>, m: Monoid<A>): A =
    la.foldLeft(m.nil, m::combine)
//end::init5[]

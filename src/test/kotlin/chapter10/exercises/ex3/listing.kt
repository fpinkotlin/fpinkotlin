package chapter10.exercises.ex3

import arrow.core.compose
import chapter10.Monoid

//tag::init1[]
fun <A> endoMonoid(): Monoid<(A) -> A> = TODO()
//end::init1[]

//tag::init2[]
fun <A> endoMonoidComposed(): Monoid<(A) -> A> =
    object : Monoid<(A) -> A> {
        override fun op(a1: (A) -> A, a2: (A) -> A): (A) -> A =
            a1 compose a2

        override val zero: (A) -> A
            get() = { it }
    }
//end::init2[]

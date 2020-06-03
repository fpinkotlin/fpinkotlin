package chapter10.solutions.ex3

import arrow.core.compose
import chapter10.Monoid

//tag::init1[]
fun <A> endoMonoid(): Monoid<(A) -> A> =
    object : Monoid<(A) -> A> {
        override fun combine(a1: (A) -> A, a2: (A) -> A): (A) -> A =
            { a -> a1(a2(a)) }

        override val nil: (A) -> A
            get() = { a -> a }
    }
//end::init1[]

//tag::init2[]
fun <A> endoMonoidComposed(): Monoid<(A) -> A> =
    object : Monoid<(A) -> A> {
        override fun combine(a1: (A) -> A, a2: (A) -> A): (A) -> A =
            a1 compose a2

        override val nil: (A) -> A
            get() = { it }
    }
//end::init2[]

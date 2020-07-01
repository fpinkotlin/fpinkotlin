package chapter10.exercises.ex2

import arrow.core.Option
import chapter10.Monoid

//tag::init1[]
fun <A> optionMonoid(): Monoid<Option<A>> = TODO()
//end::init1[]

//tag::init2[]
fun <A> dual(m: Monoid<A>): Monoid<A> = TODO()
//end::init2[]

fun <A> firstOptionMonoid() = optionMonoid<A>()

fun <A> lastOptionMonoid() = dual(firstOptionMonoid<A>())

package chapter10.exercises.ex2

import arrow.core.Option
import chapter10.Monoid
import utils.SOLUTION_HERE

//tag::init1[]
fun <A> optionMonoid(): Monoid<Option<A>> =

    SOLUTION_HERE()
//end::init1[]

//tag::init2[]
fun <A> dual(m: Monoid<A>): Monoid<A> =

    SOLUTION_HERE()
//end::init2[]

fun <A> firstOptionMonoid() = optionMonoid<A>()

fun <A> lastOptionMonoid() = dual(firstOptionMonoid<A>())

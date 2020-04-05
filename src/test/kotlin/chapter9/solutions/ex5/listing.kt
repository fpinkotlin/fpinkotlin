package chapter9.solutions.ex5

import chapter9.exercises.ex5.Parser

interface Parser<A>

//tag::init1[]
fun <A> defer(pa: Parser<A>): () -> Parser<A> = { pa }
//end::init1[]

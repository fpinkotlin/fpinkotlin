package chapter9.exercises.ex5

interface Parser<A>

//tag::init1[]
fun <A> defer(pa: Parser<A>): () -> Parser<A> = TODO()
//end::init1[]

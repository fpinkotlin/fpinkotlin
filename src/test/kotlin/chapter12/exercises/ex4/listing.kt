package chapter12.exercises.ex4

import chapter12.sec4.Stream

interface Listing {
    //tag::init1[]
    fun <A> sequence(lsa: List<Stream<A>>): Stream<List<A>>
    //end::init1[]
}

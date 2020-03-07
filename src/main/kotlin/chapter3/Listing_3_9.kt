package chapter3

object Listing_3_9 {
    //tag::init[]
    fun <A> append(a1: List<A>, a2: List<A>): List<A> =
        when (a1) {
            is Nil -> a2
            is Cons -> Cons(a1.head, append(a1.tail, a2))
        }
    //end::init[]
}

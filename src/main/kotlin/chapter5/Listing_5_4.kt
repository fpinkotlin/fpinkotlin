package chapter5

object Listing_5_4 {
    //tag::ones[]
    fun ones(): Stream<Int> = Stream.cons({ 1 }, { ones() })
    //end::ones[]

    fun <A> Stream<A>.tails(): Stream<Stream<A>> = TODO()

    fun <A> Stream<A>.exists(p: (A) -> Boolean): Boolean = TODO()

    fun <A> Stream<A>.startsWith(that: Stream<A>): Boolean = TODO()

    //tag::hassubsequence[]
    fun <A> Stream<A>.hasSubsequence(s: Stream<A>): Boolean =
        this.tails().exists { it.startsWith(s) }
    //end::hassubsequence[]
}

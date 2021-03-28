package chapter5.sec4

//tag::init1[]
fun ones(): Stream<Int> = Stream.cons({ 1 }, { ones() })
//end::init1[]

sealed class Stream<out A> {
    companion object {
        fun <A> cons(hd: () -> A, tl: () -> Stream<A>): Stream<A> = TODO()
    }

    //tag::init2[]
    fun <A> hasSubsequence(s: Stream<A>): Boolean =
        this.tails().exists { it.startsWith(s) }
    //end::init2[]
}

fun <A> Stream<A>.tails(): Stream<Stream<A>> = TODO()

fun <A> Stream<A>.exists(p: (A) -> Boolean): Boolean = TODO()

fun <A> Stream<A>.startsWith(that: Stream<A>): Boolean = TODO()

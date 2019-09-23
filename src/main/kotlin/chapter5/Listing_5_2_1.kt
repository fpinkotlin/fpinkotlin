package chapter5

import chapter4.None
import chapter4.Option
import chapter4.Some

object Listing_5_2_1 {
    //tag::init[]
    fun <A> Stream<A>.headOption(): Option<A> =
            when (this) {
                is Empty -> None
                is Cons -> Some(h())
            }
    //end::init[]

    val tl: Stream<String> = TODO()
    fun expensive(c: String): String = TODO()
    val y: String = TODO()

    val x = Cons({ expensive(y) }, { tl })
    val h1 = x.headOption()
    val h2 = x.headOption()
}
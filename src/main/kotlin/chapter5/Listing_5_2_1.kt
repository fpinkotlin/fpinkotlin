package chapter5

import chapter4.None
import chapter4.Option
import chapter4.Some

object Listing_5_2_1 {
    //tag::init[]
    fun <A> Stream<A>.headOption(): Option<A> =
        when (this) {
            is Empty -> None
            is Cons -> Some(head()) // <1>
        }
    //end::init[]

    val tl: Stream<String> = TODO()
    fun expensive(c: String): String = TODO()
    val y: String = TODO()

    //tag::init2[]
    val x = Cons({ expensive(y) }, { tl })
    val h1 = x.headOption()
    val h2 = x.headOption()
    //end::init2[]
}

package chapter5

import chapter5.Stream.Companion.thnk

object Listing_5_4 {
    //tag::ones[]
    fun ones(): Stream<Int> = Stream.cons(thnk(1), thnk(ones()))
    //end::ones[]
}
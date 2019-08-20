package chapter4

import chapter4.Listing_4_4.Try

object Listing_4_5 {

    fun parseInts(a: List<String>): Option<List<Int>> =
            sequence(a.map { str -> Try { str.toInt() } })

}
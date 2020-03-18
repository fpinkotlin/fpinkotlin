package chapter3

import kotlin.random.Random

object Listing_3_5 {
    //tag::init[]
    val x = Random.nextInt(-10, 10)
    val y: String = if (x == 0) { // <1>
        "x is zero"
    } else if (x < 0) { // <2>
        "is negative"
    } else { // <3>
        "x is positive"
    }
    //end::init[]
}

package chapter3

import kotlin.random.Random

object Listing_3_6 {
    //tag::init[]
    val x = Random.nextInt(-10, 10)
    val y: String = when { // <1>
        x == 0 -> // <2>
            "x is zero"
        x < 0 -> // <2>
            "x is negative"
        else -> // <3>
            "x is positive"
    }
    //end::init[]
}

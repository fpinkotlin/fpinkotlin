package chapter6

import chapter6.Section_6_1.RNG
import chapter6.Section_6_2.SimpleRNG

object Section_6_2 {
    //tag::init[]
    data class SimpleRNG(val seed: Long) : RNG {
        override fun nextInt(): Pair<Int, RNG> {
            val newSeed =
                (seed * 0x5DEECE66DL + 0xBL) and
                    0xFFFFFFFFFFFFL // <1>
            val nextRNG = SimpleRNG(newSeed) // <2>
            val n = (newSeed ushr 16).toInt() // <3>
            return Pair(n, nextRNG) // <4>
        }
    }
    //end::init[]
}

fun main() {
    val rng = SimpleRNG(42)

    val (n1, rng2) = rng.nextInt()
    println("n1:$n1; rng2:$rng2")
    //n1:16159453; rng2:SimpleRNG(seed=1059025964525)

    val (n2, rng3) = rng2.nextInt()
    println("n2:$n2; rng3:$rng3")
    //n2:-1281479697; rng3:SimpleRNG(seed=197491923327988)
}

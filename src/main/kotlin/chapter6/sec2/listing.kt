package chapter6.sec2

interface RNG {
    fun nextInt(): Pair<Int, RNG>
}

//tag::init1[]
data class SimpleRNG(val seed: Long) : RNG {
    override fun nextInt(): Pair<Int, RNG> {
        val newSeed =
            (seed * 0x5DEECE66DL + 0xBL) and
                0xFFFFFFFFFFFFL // <1>
        val nextRNG = SimpleRNG(newSeed) // <2>
        val n = (newSeed ushr 16).toInt() // <3>
        return n to nextRNG // <4>
    }
}
//end::init1[]

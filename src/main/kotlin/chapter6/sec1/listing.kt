package chapter6.sec1

//tag::init1[]
fun rollDie(): Int { // <1>
    val rng = kotlin.random.Random
    return rng.nextInt(6) // <2>
}
//end::init1[]

//tag::init2[]
fun rollDie2(rng: kotlin.random.Random): Int = rng.nextInt(6)
//end::init2[]

//tag::init3[]
interface RNG {
    fun nextInt(): Pair<Int, RNG>
}
//end::init3[]

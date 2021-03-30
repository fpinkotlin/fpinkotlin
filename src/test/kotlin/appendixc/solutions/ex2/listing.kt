package appendixc.solutions.ex2

import chapter8.RNG
//import chapter8.SimpleRNG
import chapter8.solutions.ex10.State
import utils.SOLUTION_HERE

//tag::init1[]
fun genStringInt(g: Gen<Int>): Gen<(String) -> Int> =

    SOLUTION_HERE()
//end::init1[]

//tag::init2[]
fun <A> genStringFn(g: Gen<A>): Gen<(String) -> A> =

    SOLUTION_HERE()
//end::init2[]

//tag::init3[]
data class Gen<out A>(val sample: State<RNG, A>)
//end::init3[]

/*
//tag::init4[]
fun <A> genStringFn(g: Gen<A>): Gen<(String) -> A> = Gen {
    State { (rng: RNG) -> ??? }
}
//end::init4[]
*/

/*
//tag::init5[]
fun <A> genStringFn(g: Gen<A>): Gen<(String) -> A> = Gen {
    State { (rng: RNG) ->
        {
            // we still use `rng` to produce a seed, so we get a new function each time
            val (seed, rng2) = rng.nextInt()
            val f: (String) -> A = s: String -> g.sample.run(
                    SimpleRNG(seed.toLong() xor s.hashCode().toLong())).first
            return (f, rng2)
        }
    }
}
//end::init5[]
*/

//tag::cogen1[]
interface Cogen<in A> {
    fun sample(a: A, rng: RNG): RNG
}
//end::cogen1[]

//tag::cogen2[]
fun <A, B> fn(cin: Cogen<A>, cout: Gen<B>): Gen<(A) -> B> =

    SOLUTION_HERE()
//end::cogen2[]

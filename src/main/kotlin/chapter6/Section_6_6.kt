package chapter6

import arrow.core.Id
import arrow.core.Tuple2
import arrow.core.extensions.id.monad.monad
import arrow.mtl.State
import arrow.mtl.extensions.fx
import chapter6.Section_6_1.RNG

object Section_6_6 {

    val int: State<RNG, Int> = TODO()

    fun ints(x: Int): State<RNG, List<Int>> = TODO()

    fun <A, B> flatMap(f: State<RNG, A>, g: (A) -> State<RNG, B>): State<RNG, B> = TODO()

    fun <A, B> map(s: State<RNG, A>, f: (A) -> B): State<RNG, B> = TODO()

    //tag::init1[]
    val ns: State<RNG, List<Int>> =
        flatMap(int) { x -> // <1>
            flatMap(int) { y ->
                map(ints(x)) { xs -> //<2>
                    xs.map { it % y } // <3>
                }
            }
        }
    //end::init1[]

    //tag::init2[]
    val ns2: State<RNG, List<Int>> =
        State.fx(Id.monad()) {
            val (x: Int) = int // <1>
            val (y: Int) = int
            val (xs: List<Int>) = ints(x) // <2>
            xs.map { it % y } // <3>
        }
    //end::init2[]

    //tag::init3[]
    fun <S> modify(f: (S) -> S): State<S, Unit> =
        State.fx(Id.monad()) {
            // <1>
            val (s: S) = get<S>() // <2>
            val (_) = set(f(s)) // <3>
        }
    //end::init3[]

    //tag::init4[]
    fun <S> get(): State<S, S> =
        State { s -> Tuple2(s, s) }
    //end::init4[]

    //tag::init5[]
    fun <S> set(s: S): State<S, Unit> =
        State { Tuple2(s, Unit) }
    //end::init5[]

}

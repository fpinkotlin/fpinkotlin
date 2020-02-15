package chapter8.sec2

import arrow.core.Either
import arrow.core.Option
import arrow.core.extensions.list.foldable.firstOption
import arrow.core.lastOrNone
import chapter6.Section_6_1.RNG
import io.kotlintest.properties.Gen
import io.kotlintest.properties.forAll

val listing1 = {

    //tag::init1[]
    val intList = Gen.list(Gen.choose(0, 100))

    forAll(intList) {
        (it.reversed().reversed() == it) and
                (it.firstOption() == it.reversed().lastOrNone())
    }
    //end::init1[]
}

val listing2 = {
    //tag::init2[]
    fun <A> listOf(a: Gen<A>): List<Gen<A>> = TODO()
    //end::init2[]
}

val listing3 = {
    //tag::init3[]
    fun <A> listOfN(n: Int, a: Gen<A>): List<Gen<A>> = TODO()
    //end::init3[]
}

val listing4 = {
    class Prop

    //tag::init4[]
    fun <A> forAll(a: Gen<A>, f: (A) -> Boolean): Prop = TODO()
    //end::init4[]
}

object listing5 {
    //tag::init5[]
    class Prop {
        fun and(p: Prop): Prop = TODO()
    }
    //end::init5[]
}

object listing6 {
    //tag::init6[]
    class Prop {
        fun check(): Unit = TODO()
        fun and(p: Prop): Prop = TODO()
    }
    //end::init6[]
}

//tag::successcount[]
typealias SuccessCount = Int
//end::successcount[]

object listing7 {
    //tag::init7[]
    class Prop {
        fun check(): Either<String, SuccessCount> = TODO()
        fun and(p: Prop): Prop = TODO()
    }
    //end::init7[]
}

//tag::failedcase[]
typealias FailedCase = String
//end::failedcase[]

object listing8 {
    //tag::init8[]
    class Prop {
        fun check(): Either<Pair<FailedCase, SuccessCount>, SuccessCount> =
            TODO()

        fun and(p: Prop): Prop = TODO()
    }
    //end::init8[]
}

object Listing9 {
    //tag::init9[]
    interface RNG {
        fun nextInt(): Pair<Int, RNG>
    }

    data class State<S, out A>(val run: (S) -> Pair<A, S>)

    data class Gen<A>(val sample: State<RNG, A>)
    //end::init9[]
}

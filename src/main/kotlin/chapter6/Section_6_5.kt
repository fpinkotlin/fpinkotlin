package chapter6

object Section_6_5 {
    //tag::init1[]
    fun <S, A, B> map(
        sa: (S) -> Pair<A, S>,
        f: (A) -> (B)
    ): (S) -> Pair<B, S> = TODO()
    //end::init1[]

    //tag::init3[]
    data class State<S, out A>(val run: (S) -> Pair<A, S>)
    //end::init3[]
}

//tag::init2[]
typealias State<S, A> = (S) -> Pair<A, S>
//end::init2[]

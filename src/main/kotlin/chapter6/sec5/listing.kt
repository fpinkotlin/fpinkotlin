package chapter6.sec5

//tag::init1[]
fun <S, A, B> map(
    sa: (S) -> Pair<A, S>,
    f: (A) -> B
): (S) -> Pair<B, S> = TODO()
//end::init1[]

//tag::init2[]
data class State<S, out A>(val run: (S) -> Pair<A, S>)
//end::init2[]

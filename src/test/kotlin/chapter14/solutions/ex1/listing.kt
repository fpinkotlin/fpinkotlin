package chapter14.solutions.ex1

import chapter14.sec2.ST
import chapter14.sec2.STArray

//tag::init[]
fun <S, A> STArray<S, A>.fill(xs: Map<Int, A>): ST<S, Unit> =
    xs.entries.fold(ST { Unit }) { st, (k, v) ->
        st.flatMap { write(k, v) }
    }
//end::init[]

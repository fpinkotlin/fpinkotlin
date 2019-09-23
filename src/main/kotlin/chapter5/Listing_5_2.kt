package chapter5

//tag::init[]
sealed class Stream<out A> {
    companion object {

        fun <A> cons(hd: () -> A, tl: () -> Stream<A>): Stream<A> { // <1>
            val head: A by lazy { hd() } // <2>
            val tail: Stream<A> by lazy { tl() } // <3>
            return Cons({ head }, { tail })
        }

        fun <A> of(vararg xs: A): Stream<A> = // <4>
                if (xs.isEmpty()) empty()
                else cons(thnk(xs[0]), thnk(of(*xs.sliceArray(1 until xs.size))))

        fun <A> empty(): Stream<A> = Empty // <5>

        fun <A> thnk(a: A): () -> A = { -> a }
    }
}

data class Cons<out A>(val h: () -> A, val t: () -> Stream<A>) : Stream<A>() // <6>

object Empty : Stream<Nothing>()
//end::init[]


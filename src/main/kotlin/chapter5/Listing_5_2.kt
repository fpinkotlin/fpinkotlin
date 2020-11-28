package chapter5

//tag::init[]
sealed class Stream<out A> {

    //tag::companion[]
    companion object {

        //smart constructors
        //tag::cons[]
        fun <A> cons(hd: () -> A, tl: () -> Stream<A>): Stream<A> {
            val head: A by lazy(hd)
            val tail: Stream<A> by lazy(tl)
            return Cons({ head }, { tail })
        }
        //end::cons[]
        //tag::of[]

        fun <A> of(vararg xs: A): Stream<A> =
            if (xs.isEmpty()) empty()
            else cons({ xs[0] },
                { of(*xs.sliceArray(1 until xs.size)) })
        //end::of[]
        //tag::empty[]

        fun <A> empty(): Stream<A> = Empty
        //end::empty[]

        fun <A> continually(a: A): Stream<A> =
            cons({ a }, { continually(a) })
    }
    //end::companion[]
}

data class Cons<out A>(
    val head: () -> A,
    val tail: () -> Stream<A>
) : Stream<A>()

object Empty : Stream<Nothing>()
//end::init[]

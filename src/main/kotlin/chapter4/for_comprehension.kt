package chapter4

object ForComprehension {

    fun <A, B> Option<A>.flatMap(f: (A) -> Option<B>): Option<B> = TODO()
    fun <A, B> Option<A>.map(f: (A) -> B): Option<B> = TODO()
    fun <A> Option.Companion.fx(f: () -> Unit): Option<A> = TODO()
    fun <A> Option<A>.bind(): A = TODO()

    object FlatMapExample {
        //tag::map2[]
        fun <A, B, C> map2(
            oa: Option<A>,
            ob: Option<B>,
            f: (A, B) -> C
        ): Option<C> =
            oa.flatMap { a ->
                ob.map { b ->
                    f(a, b)
                }
            }
        //end::map2[]
    }

    object BindingExample {
        //tag::binding[]
        fun <A, B, C> map2(
            oa: Option<A>,
            ob: Option<B>,
            f: (A, B) -> C
        ): Option<C> =
            Option.fx {
                val a = oa.bind()
                val b = ob.bind()
                f(a, b)
            }
        //end::binding[]
    }
}

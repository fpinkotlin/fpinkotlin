package chapter4

import arrow.core.Option
import arrow.core.extensions.fx

object ForComprehensionSidebar {

    object FlatMapExample {
        //tag::flatmap[]
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
        //end::flatmap[]
    }

    object BindingExample {
        //tag::binding[]
        fun <A, B, C> map2(
            oa: Option<A>,
            ob: Option<B>,
            f: (A, B) -> C
        ): Option<C> =
            Option.fx {
                val (a) = oa
                val (b) = ob
                f(a, b)
            }
        //end::binding[]
    }
}

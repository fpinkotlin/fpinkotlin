package chapter3

object VariadicFunctionSidebar {
    //tag::init[]
    fun <A> of(vararg aa: A): List<A> {
        val tail = aa.sliceArray(1 until aa.size)
        return if (aa.isEmpty()) Nil else Cons(aa[0], List.of(*tail))
    }
    //end::init[]
}

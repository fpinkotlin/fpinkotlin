package chapter2.exercises

object Exercise_2_2 {
    // tag::exercise2.2[]
    val <T> List<T>.tail: List<T>
        get() = drop(1)

    val <T> List<T>.head: T
        get() = first()

    fun <A> isSorted(aa: List<A>, ordered: (A, A) -> Boolean): Boolean = TODO()
    // end::exercise2.2[]
}
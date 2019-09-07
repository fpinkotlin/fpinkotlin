package chapter2.exercises

object Exercise_2_2 {
  // tag::init[]
  val <T> List<T>.tail: List<T>
    get() = drop(1)

  val <T> List<T>.head: T
    get() = first()

  fun <A> isSorted(aa: List<A>, ord: (A, A) -> Boolean): Boolean = TODO()
  // end::init[]
}
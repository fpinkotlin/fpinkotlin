package chapter5

import chapter3.List
import chapter4.Option
import chapter5.Stream.Companion.cons
import chapter5.Stream.Companion.empty
import chapter5.Stream.Companion.thnk
import chapter3.Cons as ConsL
import chapter3.Nil as NilL

object Listing_5_3 {

    fun <A, B> Stream<A>.map(f: (A) -> B): Stream<B> = TODO()

    fun <A> Stream<A>.filter(f: (A) -> Boolean): Stream<A> = TODO()

    fun <A> Stream<A>.toList(): List<A> = TODO()

    fun <A> Stream<A>.headOption(): Option<A> = TODO()

    //tag::exists1[]
    fun <A> Stream<A>.exists1(p: (A) -> Boolean): Boolean =
            when (this) {
                is Cons -> p(this.h()) || this.t().exists1(p)
                else -> false
            }
    //end::exists1[]

    //tag::init[]
    fun <A, B> Stream<A>.foldRight(z: () -> B, f: (A, () -> B) -> B): B =
            when (this) {
                is Cons -> f(this.h(), thnk(t().foldRight(z, f)))
                else -> z()
            }
    //end::init[]

    //tag::exists2[]
    fun <A> Stream<A>.exists2(p: (A) -> Boolean): Boolean =
            foldRight(thnk(false), { a, b -> p(a) || b() })
    //end::exists2[]

    //tag::trace[]
    val listing = {
        Stream.of(1, 2, 3, 4).map { it + 10 }.filter { it % 2 == 0 }.toList()
        cons(thnk(11), thnk(Stream.of(2, 3, 4))).filter { it % 2 == 0 }.toList()
        Stream.of(2, 3, 4).map { it + 10 }.filter { it % 2 == 0 }.toList()
        cons(thnk(12), thnk(Stream.of(3, 4))).filter { it % 2 == 0 }.toList()
        ConsL(12, Stream.of(3, 4).map { it + 10 }.filter { it % 2 == 0 }.toList())
        ConsL(12, cons(thnk(13), thnk(Stream.of(4))).filter { it % 2 == 0 }.toList())
        ConsL(12, Stream.of(4).map { it + 10 }.filter { it % 2 == 0 }.toList())
        ConsL(12, cons(thnk(14), thnk(empty<Int>())).filter { it % 2 == 0 }.toList())
        ConsL(12, ConsL(14, empty<Int>().map { it + 10 }.filter { it % 2 == 0 }.toList()))
        ConsL(12, ConsL(14, NilL))
        List.of(12, 14)
    }
    //end::trace[]

    //tag::find[]
    fun <A> Stream<A>.find(p: (A) -> Boolean): Option<A> = filter(p).headOption()
    //end::find[]
}
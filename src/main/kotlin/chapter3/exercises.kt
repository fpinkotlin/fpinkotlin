package chapter3

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

//Exercise 3.1

// tag::exercise3.1[]
fun <A> tail(xs: List<A>): List<A> = when (xs) {
    is Cons -> xs.tail
    is Nil -> throw Exception("Nil cannot have a `tail`")
}
// end::exercise3.1[]

//Exercise 3.2

// tag::exercise3.2[]
fun <A> setHead(xs: List<A>, x: A): List<A> = when (xs) {
    is Cons -> Cons(x, xs.tail)
    is Nil -> throw Exception("Cannot replace `head` of a Nil list")
}
// end::exercise3.2[]

//Exercise 3.3

// tag::exercise3.3[]
fun <A> drop(l: List<A>, n: Int): List<A> =
        if (n == 0) l
        else when (l) {
            is Cons -> drop(l.tail, n - 1)
            is Nil -> throw Exception("Cannot drop more elements than in list")
        }
// tag::exercise3.3[]

//Exercise 3.4

// tag::exercise3.4[]
fun <A> dropWhile(l: List<A>, f: (A) -> Boolean): List<A> =
        when (l) {
            is Cons -> if (f(l.head)) dropWhile(l.tail, f) else l
            is Nil -> l
        }
// end::exercise3.4[]

//Exercise 3.5

// tag::exercise3.5[]
fun <A> init(l: List<A>): List<A> =
        when (l) {
            is Cons ->
                if (l.tail == Nil) Nil
                else Cons(l.head, init(l.tail))
            is Nil ->
                throw Exception("Cannot init Nil list")
        }
// end::exercise3.5[]

//Exercise 3.17
fun <A, B> map(ss: List<A>, f: (A) -> B): List<B> = TODO()

//Exercise 3.18
fun <A> filter(ls: List<A>, f: (A) -> Boolean): List<A> = TODO()

//Exercise 3.19
fun <A, B> flatMap(ss: List<A>, f: (A) -> List<B>): List<B> = TODO()

fun main() {
    val xs = List.of(1, 2, 3, 4, 5)

    //Exercise 3.1
    assertThat(tail(xs))
            .isEqualTo(List.of(2, 3, 4, 5))
    assertThatThrownBy { tail(Nil) }
            .isInstanceOf(Exception::class.java)
            .hasMessage("Nil cannot have a `tail`")

    //Exercise 3.2
    assertThat(setHead(xs, 6))
            .isEqualTo(List.of(6, 2, 3, 4, 5))
    assertThatThrownBy { setHead(Nil, 1) }
            .isInstanceOf(Exception::class.java)
            .hasMessage("Cannot replace `head` of a Nil list")

    //Exercise 3.3
    assertThat(drop(xs, 2))
            .isEqualTo(List.of(3, 4, 5))
    assertThat(drop(xs, 5))
            .isEqualTo(Nil)
    assertThatThrownBy { drop(xs, 6) }
            .isInstanceOf(Exception::class.java)
            .hasMessage("Cannot drop more elements than in list")

    //Exercise 3.4
    assertThat(dropWhile(xs) { it <= 3 })
            .isEqualTo(List.of(4, 5))
    assertThat(dropWhile(xs) { it != 10 })
            .isEqualTo(Nil)

    //Exercise 3.5
    assertThat(init(xs))
            .isEqualTo(List.of(1, 2, 3, 4))
    assertThat(init(List.of(1)))
            .isEqualTo(Nil)
    assertThatThrownBy { init(Nil) }
            .isInstanceOf(Exception::class.java)
            .hasMessage("Cannot init Nil list")

}

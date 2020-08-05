package chapter12.solutions.ex1

import arrow.Kind
import chapter10.ForOption
import chapter10.None
import chapter10.Some
import chapter10.fix
import chapter12.Cons
import chapter12.ForList
import chapter12.Functor
import chapter12.List
import chapter12.ListOf
import chapter12.fix
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

interface Applicative<F> : Functor<F> {

    fun <A, B, C> map2(
        fa: Kind<F, A>,
        fb: Kind<F, B>,
        f: (A, B) -> C
    ): Kind<F, C>

    fun <A> unit(a: A): Kind<F, A>

    override fun <A, B> map(
        fa: Kind<F, A>,
        f: (A) -> B
    ): Kind<F, B> =
        map2(fa, unit(Unit)) { a, _ -> f(a) }

    fun <A, B> traverse(
        la: List<A>,
        f: (A) -> Kind<F, B>
    ): Kind<F, List<B>> =
        la.foldRight(
            unit(List.empty<B>()),
            { a: A, acc: Kind<F, List<B>> ->
                map2(f(a), acc) { b: B, lb: List<B> -> Cons(b, lb) }
            }
        )

    //tag::init1[]
    fun <A> sequence(lfa: List<Kind<F, A>>): Kind<F, List<A>> =
        traverse(lfa) { it }

    fun <A> replicateM(n: Int, ma: Kind<F, A>): Kind<F, List<A>> =
        sequence(List.fill(n, ma))

    fun <A, B> product(
        ma: Kind<F, A>,
        mb: Kind<F, B>
    ): Kind<F, Pair<A, B>> =
        map2(ma, mb) { a, b -> Pair(a, b) }
    //end::init1[]
}

class Exercise1 : WordSpec({
    "product" should {
        "return all product permutations of lists" {
            val AF = object : Applicative<ForList> {
                override fun <A, B, C> map2(
                    fa: ListOf<A>,
                    fb: ListOf<B>,
                    f: (A, B) -> C
                ): ListOf<C> =
                    fa.fix().flatMap { a: A ->
                        fb.fix().flatMap { b: B ->
                            List.of(f(a, b))
                        }
                    }

                override fun <A> unit(a: A): ListOf<A> = List.of(a)
            }

            AF.product(
                List.of(1, 2, 3, 4),
                List.of("5", "6", "7", "8")
            ).fix() shouldBe List.of(
                Pair(1, "5"), Pair(1, "6"), Pair(1, "7"), Pair(1, "8"),
                Pair(2, "5"), Pair(2, "6"), Pair(2, "7"), Pair(2, "8"),
                Pair(3, "5"), Pair(3, "6"), Pair(3, "7"), Pair(3, "8"),
                Pair(4, "5"), Pair(4, "6"), Pair(4, "7"), Pair(4, "8")
            )
        }

        "return all product permutations of options" {
            val AF = object : Applicative<ForOption> {
                override fun <A, B, C> map2(
                    fa: Kind<ForOption, A>,
                    fb: Kind<ForOption, B>,
                    f: (A, B) -> C
                ): Kind<ForOption, C> =
                    fa.fix().flatMap { a ->
                        fb.fix().flatMap { b ->
                            Some(f(a, b))
                        }
                    }

                override fun <A> unit(a: A): Kind<ForOption, A> = Some(a)
            }

            AF.product(None, None).fix() shouldBe None
            AF.product(None, Some("1")).fix() shouldBe None
            AF.product(Some(1), None).fix() shouldBe None
            AF.product(Some(1), Some("1")).fix() shouldBe
                Some(Pair(1, "1"))
        }
    }
})

package chapter12.exercises.ex15

import arrow.Kind
import arrow.core.ForId
import arrow.core.ForListK
import arrow.core.Id
import arrow.core.IdOf
import arrow.core.ListK
import arrow.core.ListKOf
import arrow.core.extensions.id.apply.map2
import arrow.core.fix
import arrow.core.k
import arrow.syntax.function.tupled
import chapter10.Foldable
import chapter11.State
import chapter11.fix
import chapter12.Functor
import chapter12.sec7_2.Applicative
import chapter12.sec7_2.stateMonad
import chapter12.sec7_2.stateMonadApplicative
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import utils.SOLUTION_HERE

val idApplicative: Applicative<ForId> =
    object : Applicative<ForId> {
        override fun <A> unit(a: A): IdOf<A> = Id(a)

        override fun <A, B, C> map2(
            fa: IdOf<A>,
            fb: IdOf<B>,
            f: (A, B) -> C
        ): IdOf<C> =
            fa.fix().map2(fb, f.tupled())

        override fun <A, B> map(
            fa: IdOf<A>,
            f: (A) -> B
        ): IdOf<B> =
            fa.fix().map(f)
    }

interface Traversable<F> : Functor<F>, Foldable<F> {

    override fun <A, B> map(fa: Kind<F, A>, f: (A) -> B): Kind<F, B> =
        TODO()

    override fun <A, B> foldRight(
        fa: Kind<F, A>,
        z: B,
        f: (A, B) -> B
    ): B = TODO()

    fun <G, A> sequence(
        fga: Kind<F, Kind<G, A>>,
        AG: Applicative<G>
    ): Kind<G, Kind<F, A>> =
        traverse(fga, AG) { it }

    fun <G, A, B> traverse(
        fa: Kind<F, A>,
        AG: Applicative<G>,
        f: (A) -> Kind<G, B>
    ): Kind<G, Kind<F, B>> =
        sequence(map(fa, f), AG)

    fun <S, A, B> traverseS(
        fa: Kind<F, A>,
        f: (A) -> State<S, B>
    ): State<S, Kind<F, B>> =
        traverse(fa, stateMonadApplicative(stateMonad<S>())) { a ->
            f(a).fix()
        }.fix()

    fun <S, A, B> mapAccum(
        fa: Kind<F, A>,
        s: S,
        f: (A, S) -> Pair<B, S>
    ): Pair<Kind<F, B>, S> =
        traverseS(fa) { a: A ->
            State.get<S>().flatMap { s1 ->
                val (b, s2) = f(a, s1)
                State.set(s2).map { _ -> b }
            }
        }.run(s)

    fun <A> toList(ta: Kind<F, A>): List<A> =
        mapAccum(ta, emptyList()) { a: A, la: List<A> ->
            Unit to (listOf(a) + la)
        }.second.reversed()

    //tag::init[]
    fun <A> reverse(ta: Kind<F, A>): Kind<F, A> =

        SOLUTION_HERE()
    //end::init[]
}

val T = object : Traversable<ForListK> {
    override fun <G, A, B> traverse(
        fa: ListKOf<A>,
        AG: Applicative<G>,
        f: (A) -> Kind<G, B>
    ): Kind<G, ListKOf<B>> =
        fa.fix().foldLeft(AG.unit(ListK.empty<B>())) { acc, a ->
            AG.map2(acc, f(a)) { lb: List<B>, b: B -> ListK(lb + b) }
        }
}

//TODO: Enable tests by removing `!` prefix
class Exercise15 : WordSpec({

    val x = listOf(1, 2, 3, 4, 5).k()
    val y = listOf(6, 7, 8, 9, 10).k()

    "reverse" should {
        "!reverse the order of any traversable functor" {
            T.reverse(
                listOf(1, 2, 3, 4, 5).k()
            ) shouldBe listOf(5, 4, 3, 2, 1)
        }

        "!follow the law" {
            T.toList(T.reverse(x)) + T.toList(T.reverse(y)) shouldBe
                T.reverse((T.toList(y) + T.toList(x)).k())
        }
    }
})

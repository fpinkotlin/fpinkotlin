package chapter12

import arrow.Kind
import arrow.Kind2
import arrow.higherkind
import arrow.syntax.function.curried
import chapter11.sec2.Monad

interface Functor<F> {
    fun <A, B> map(fa: Kind<F, A>, f: (A) -> B): Kind<F, B>
}

interface Applicative<F> : Functor<F> {

    override fun <A, B> map(
        fa: Kind<F, A>,
        f: (A) -> B
    ): Kind<F, B> =
        map2(fa, unit(Unit)) { a, _ -> f(a) }

    fun <A, B> apply(
        fab: Kind<F, (A) -> B>,
        fa: Kind<F, A>
    ): Kind<F, B> =
        map2(fa, fab) { a, f -> f(a) }

    fun <A> unit(a: A): Kind<F, A>

    fun <A, B, C> map2(
        fa: Kind<F, A>,
        fb: Kind<F, B>,
        f: (A, B) -> C
    ): Kind<F, C> =
        apply(apply(unit(f.curried()), fa), fb)

    fun <A, B, C, D> map3(
        fa: Kind<F, A>,
        fb: Kind<F, B>,
        fc: Kind<F, C>,
        f: (A, B, C) -> D
    ): Kind<F, D> =
        apply(apply(apply(unit(f.curried()), fa), fb), fc)

    fun <A, B, C, D, E> map4(
        fa: Kind<F, A>,
        fb: Kind<F, B>,
        fc: Kind<F, C>,
        fd: Kind<F, D>,
        f: (A, B, C, D) -> E
    ): Kind<F, E> =
        apply(apply(apply(apply(unit(f.curried()), fa), fb), fc), fd)
}

interface Traversable<F> : Functor<F> {

    fun <G, A, B> traverse(
        fa: Kind<F, A>,
        AG: Applicative<G>,
        f: (A) -> Kind<G, B>
    ): Kind<G, Kind<F, B>> =
        sequence(map(fa, f), AG)

    fun <G, A> sequence(
        fga: Kind<F, Kind<G, A>>,
        AG: Applicative<G>
    ): Kind<G, Kind<F, A>> = // <2>
        traverse(fga, AG) { it }

    override fun <A, B> map(fa: Kind<F, A>, f: (A) -> B): Kind<F, B> =
        TODO()
}

@higherkind
class Product<F, G, A>(
    val value: Pair<Kind<F, A>, Kind<G, A>>
) : ProductOf<F, G, A>

@higherkind
class Composite<F, G, A>(val value: Kind<F, Kind<G, A>>) :
    CompositeOf<F, G, A>

//tag::init3[]
@higherkind
class Fusion<F, G, H, B>(
    val value: Pair<Kind<G, Kind<F, B>>, Kind<H, Kind<F, B>>>
) : FusionOf<F, G, H, B>
//end::init3[]

// List

@higherkind
sealed class List<out A> : ListOf<A> {

    companion object {

        fun <A> of(vararg aa: A): List<A> {
            val tail = aa.sliceArray(1 until aa.size)
            return if (aa.isEmpty()) Nil else Cons(aa[0], of(*tail))
        }

        fun <A> empty(): List<A> = Nil

        fun <A> append(a1: List<A>, a2: List<A>): List<A> =
            a1.foldRight(a2, { x, y -> Cons(x, y) })

        fun <A> fill(n: Int, a: A): List<A> =
            when (n) {
                0 -> empty<A>()
                else -> Cons(a, fill(n - 1, a))
            }
    }

    fun <B> foldLeft(z: B, f: (B, A) -> B): B =
        foldRight(
            { b: B -> b },
            { a, g ->
                { b ->
                    g(f(b, a))
                }
            })(z)

    fun <B> foldRight(z: B, f: (A, B) -> B): B =
        when (this) {
            is Nil -> z
            is Cons -> f(this.head, this.tail.foldRight(z, f))
        }

    fun <B> flatMap(f: (A) -> List<B>): List<B> =
        foldRight(empty(), { a, acc -> append(f(a), acc) })

    fun <B> map(f: (A) -> B): List<B> =
        flatMap { of(f(it)) }

    fun reverse(): List<A> =
        foldLeft(empty(), { t: List<A>, h: A -> Cons(h, t) })
}

object Nil : List<Nothing>()

data class Cons<out A>(
    val head: A,
    val tail: List<A>
) : List<A>()

// Either

sealed class Either<E, out A> : EitherOf<E, A>

data class Left<E>(val value: E) : Either<E, Nothing>()
data class Right<out A>(val value: A) : Either<Nothing, A>()

sealed class ForEither private constructor() {
    companion object
}

typealias EitherOf<E, A> = Kind2<ForEither, E, A>

fun <E, A> EitherOf<E, A>.fix() = this as Either<E, A>

typealias EitherPartialOf<E> = Kind<ForEither, E>

interface EitherMonad<E> : Monad<EitherPartialOf<E>> {
    override fun <A> unit(a: A): EitherOf<E, A>

    override fun <A, B> flatMap(
        fa: EitherOf<E, A>,
        f: (A) -> EitherOf<E, B>
    ): EitherOf<E, B>
}

interface EitherApplicative<E> : Applicative<EitherPartialOf<E>>

//tag::init[]
@higherkind
data class Tree<out A>(val head: A, val tail: List<Tree<A>>) : TreeOf<A>
//end::init[]

fun assertEqual(o1: Any, o2: Any): Unit =
    if (o1 != o2)
        throw AssertionError("$o1 not equal to $o2")
    else Unit

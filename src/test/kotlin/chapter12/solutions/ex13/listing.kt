package chapter12.solutions.ex13

import arrow.Kind
import arrow.core.ForId
import arrow.core.Id
import arrow.core.IdOf
import arrow.core.extensions.id.apply.map2
import arrow.core.fix
import arrow.syntax.function.tupled
import chapter12.Applicative
import chapter12.Functor

//tag::init1[]
fun idApplicative(): Applicative<ForId> =
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
//end::init1[]

//tag::init2[]
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
    ): Kind<G, Kind<F, A>> =
        traverse(fga, AG) { it }

    override fun <A, B> map(fa: Kind<F, A>, f: (A) -> B): Kind<F, B> =
        traverse(fa, idApplicative()) { Id(f(it)) }.fix().extract()
}
//end::init2[]

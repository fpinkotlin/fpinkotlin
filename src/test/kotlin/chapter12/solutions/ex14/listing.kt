package chapter12.solutions.ex14

import arrow.Kind
import arrow.core.Id
import arrow.core.fix
import arrow.typeclasses.Applicative
import arrow.typeclasses.internal.IdBimonad
import chapter12.Functor

//tag::init1[]
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
        traverse(fa, IdBimonad) { Id(f(it)) }.fix().extract()
}
//end::init1[]

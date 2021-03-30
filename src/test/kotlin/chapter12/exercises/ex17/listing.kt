package chapter12.exercises.ex17

import arrow.Kind
import chapter10.Foldable
import chapter12.Applicative
import chapter12.Functor
import chapter12.Product
import chapter12.ProductOf
import chapter12.ProductPartialOf
import chapter12.fix
import utils.SOLUTION_HERE

infix fun <F, G> Applicative<F>.product(
    ag: Applicative<G>
): Applicative<ProductPartialOf<F, G>> =
    object : Applicative<ProductPartialOf<F, G>> {
        override fun <A, B> apply(
            fgab: ProductOf<F, G, (A) -> B>,
            fga: ProductOf<F, G, A>
        ): ProductOf<F, G, B> {
            val (fab, gab) = fgab.fix().value
            val (fa, ga) = fga.fix().value
            return Product(
                this@product.apply(fab, fa) to ag.apply(gab, ga)
            )
        }

        override fun <A> unit(a: A): ProductOf<F, G, A> =
            Product(this@product.unit(a) to ag.unit(a))
    }

interface Traversable<F> : Functor<F>, Foldable<F> {

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

    //tag::init[]
    fun <G, H, A, B> fuse(
        ta: Kind<F, A>,
        AG: Applicative<G>,
        AH: Applicative<H>,
        f: (A) -> Kind<G, B>,
        g: (A) -> Kind<H, B>
    ): Pair<Kind<G, Kind<F, B>>, Kind<H, Kind<F, B>>> =

        SOLUTION_HERE()
    //end::init[]
}

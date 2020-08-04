package chapter12.solutions.ex12

import arrow.Kind
import chapter10.ForOption
import chapter10.None
import chapter10.OptionOf
import chapter10.Some
import chapter10.fix
import chapter12.Applicative
import chapter12.Cons
import chapter12.ForList
import chapter12.ForTree
import chapter12.List
import chapter12.ListOf
import chapter12.Traversable
import chapter12.Tree
import chapter12.TreeOf
import chapter12.fix

//tag::init1[]
fun <A> optionTraversable() = object : Traversable<ForOption> {

    override fun <G, A, B> traverse(
        fa: OptionOf<A>,
        AG: Applicative<G>,
        f: (A) -> Kind<G, B>
    ): Kind<G, OptionOf<B>> =
        when (val o = fa.fix()) {
            is Some -> AG.map(f(o.get)) { Some(it) }
            is None -> AG.unit(None)
        }
}
//end::init1[]

//tag::init2[]
fun <A> listTraversable() = object : Traversable<ForList> {

    override fun <G, A, B> traverse(
        fa: ListOf<A>,
        AG: Applicative<G>,
        f: (A) -> Kind<G, B>
    ): Kind<G, ListOf<B>> =
        fa.fix().foldLeft(
            AG.unit(List.empty<B>())
        ) { acc: Kind<G, List<B>>, a: A ->
            AG.map2(acc, f(a)) { t, h -> Cons(h, t) }
        }
}
//end::init2[]

//tag::init3[]
fun <A> treeTraversable() = object : Traversable<ForTree> {

    override fun <G, A, B> traverse(
        fa: TreeOf<A>,
        AG: Applicative<G>,
        f: (A) -> Kind<G, B>
    ): Kind<G, TreeOf<B>> {
        val fta = fa.fix()
        return AG.map2(
            f(fta.head),
            listTraversable<A>().traverse(fta.tail, AG) { ta: Tree<A> ->
                traverse(ta, AG, f)
            }
        ) { h: B, t: ListOf<TreeOf<B>> ->
            Tree(h, t.fix().map { it.fix() })
        }
    }
}
//end::init3[]
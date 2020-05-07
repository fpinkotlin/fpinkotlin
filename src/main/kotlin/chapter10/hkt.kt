package chapter10

import arrow.Kind

/*
//tag::init1[]
interface Foldable<F<A>> {
    //some abstract methods
}

object ListFoldable : Foldable<List<A>> {
    //some code
}
//end::init1[]
*/

//tag::init2[]
class ForList private constructor() {
    companion object
}
//end::init2[]

//tag::init3[]
typealias ListOf<A> = Kind<ForList, A>
//end::init3[]

//tag::init4[]
sealed class List<out A> : ListOf<A>
//end::init4[]
{
    fun <B> foldRight(z: B, f: (A, B) -> B): B = TODO()
}

//tag::init5[]
object ListFoldable : Foldable<ForList> {
    //tag::foldright[]
    override fun <A, B> foldRight(
        fa: ListOf<A>,
        z: B,
        f: (A, B) -> B
    ): B =
    //end::foldright[]
    //tag::foldrightimpl[]
        fa.fix().foldRight(z, f)
    //end::foldrightimpl[]
}
//end::init5[]

//tag::init6[]
interface Foldable<F> {
    fun <A, B> foldRight(fa: Kind<F, A>, z: B, f: (A, B) -> B): B
}
//end::init6[]

//tag::init7[]
fun <A> ListOf<A>.fix() = this as List<A>
//end::init7[]


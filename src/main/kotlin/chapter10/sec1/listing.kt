package chapter10.sec1

//tag::init1[]
interface Monoid<A> {
    fun op(a1: A, a2: A): A // <1>
    val zero: A // <2>
}
//end::init1[]

//tag::init2[]
val stringMonoid = object : Monoid<String> {

    override fun op(a1: String, a2: String): String = a1 + a2

    override val zero: String = ""
}
//end::init2[]

//tag::init3[]
fun <A> listMonoid(): Monoid<List<A>> = object : Monoid<List<A>> {

    override fun op(a1: List<A>, a2: List<A>): List<A> = a1 + a2

    override val zero: List<A> = emptyList()
}
//end::init3[]


package chapter10.sec1

//tag::init1[]
interface Monoid<A> {
    fun combine(a1: A, a2: A): A // <1>
    val nil: A // <2>
}
//end::init1[]

//tag::init2[]
val stringMonoid = object : Monoid<String> {

    override fun combine(a1: String, a2: String): String = a1 + a2

    override val nil: String = ""
}
//end::init2[]

//tag::init3[]
fun <A> listMonoid(): Monoid<List<A>> = object : Monoid<List<A>> {

    override fun combine(a1: List<A>, a2: List<A>): List<A> = a1 + a2

    override val nil: List<A> = emptyList()
}
//end::init3[]

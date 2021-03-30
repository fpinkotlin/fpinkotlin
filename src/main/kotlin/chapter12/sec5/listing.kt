package chapter12.sec5

import arrow.Kind
import arrow.core.compose
import chapter10.ForOption
import chapter10.Option
import chapter10.Some
import chapter10.fix
import chapter10.sec1.Monoid
import chapter12.Applicative

interface Listing<F, A> : Applicative<F> {

    val f: (A) -> A
    val g: (A) -> A
    val id: (A) -> A
    val v: Kind<F, A>

    fun listing() {
        //tag::init1[]
        map(v, id) == v

        map(map(v, g), f) == map(v, (f compose g))
        //end::init1[]
    }

    //tag::init2[]
    override fun <A, B> map(
        fa: Kind<F, A>,
        f: (A) -> B
    ): Kind<F, B> =
        map2(fa, unit(Unit)) { a, _ -> f(a) }
    //end::init2[]

    val fa: Kind<F, A>
    val fb: Kind<F, A>
    val fc: Kind<F, A>

    fun listing2() {
        //tag::init4[]
        map2(unit(Unit), fa) { _, a -> a }

        map2(fa, unit(Unit)) { a, _ -> a }
        //end::init4[]
    }

    //tag::init7[]
    fun <A, B> product(
        ma: Kind<F, A>,
        mb: Kind<F, B>
    ): Kind<F, Pair<A, B>> =
        map2(ma, mb) { a, b -> a to b }
    //end::init7[]

    //tag::init8[]
    fun <A, B, C> assoc(p: Pair<A, Pair<B, C>>): Pair<Pair<A, B>, C> =
        (p.first to p.second.first) to p.second.second
    //end::init8[]

    fun listing3() {
        //tag::init9[]
        product(product(fa, fb), fc) ==
            map(product(fa, product(fb, fc)), ::assoc)
        //end::init9[]

        //tag::init14[]
        product(product(fa, fb), fc) == product(fa, product(fb, fc))
        //end::init14[]
    }

    //tag::init12[]
    fun <I1, O1, I2, O2> productF(
        f: (I1) -> O1,
        g: (I2) -> O2
    ): (I1, I2) -> Pair<O1, O2> =
        { i1, i2 -> f(i1) to g(i2) }
    //end::init12[]

    fun listing4() {
        //tag::init13[]
        map2(fa, fb, productF(f, g)) == product(map(fa, f), map(fb, g))
        //end::init13[]
    }
}

interface Listing2<F, A> : Applicative<F> {
    //tag::init3[]
    override fun <A, B> map(
        fa: Kind<F, A>,
        f: (A) -> B
    ): Kind<F, B> =
        map2(unit(Unit), fa) { _, a -> f(a) }
    //end::init3[]
}

interface Listing3<F, A> {
    //tag::init5[]
    fun <A, B, C, D> map3(
        fa: Kind<F, A>,
        fb: Kind<F, B>,
        fc: Kind<F, C>,
        f: (A, B, C) -> D
    ): Kind<F, D>
    //end::init5[]
}

interface Listing4<A> : Monoid<A> {
    val a: A
    val b: A
    val c: A
    fun listing() {
        //tag::init6[]
        combine(combine(a, b), c) == combine(a, combine(b, c))
        //end::init6[]
    }
}

val listing4 = {
    //tag::init10[]
    val A: Applicative<ForOption> = TODO()

    data class Employee(val name: String, val id: Int)
    data class Pay(val rate: Double, val daysPerYear: Int)

    fun format(oe: Option<Employee>, op: Option<Pay>): Option<String> =
        A.map2(oe, op) { e, p ->
            "${e.name} makes ${p.rate * p.daysPerYear}"
        }.fix()

    val employee = Employee("John Doe", 1)
    val pay = Pay(600.00, 240)
    val message: Option<String> =
        format(Some(employee), Some(pay))
    //end::init10[]
}

val listing5 = {
    val F: Applicative<ForOption> = TODO()

    data class Employee(val name: String, val id: Int)
    data class Pay(val rate: Double, val daysPerYear: Int)

    //tag::init11[]
    fun format(oe: Option<String>, op: Option<Double>): Option<String> =
        F.map2(oe, op) { e, p -> "$e makes $p" }.fix()

    val maybeEmployee = Some(Employee("John Doe", 1))
    val maybePay = Some(Pay(600.00, 240))

    val message: Option<String> =
        format(
            F.map(maybeEmployee) { it.name }.fix(),
            F.map(maybePay) { it.rate * it.daysPerYear }.fix()
        )
    //end::init11[]
}

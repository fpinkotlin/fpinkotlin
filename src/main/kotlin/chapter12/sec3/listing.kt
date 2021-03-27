package chapter12.sec3

import arrow.Kind
import arrow.core.ForOption
import arrow.core.Option
import arrow.core.fix
import arrow.core.toOption
import arrow.higherkind
import arrow.syntax.function.curried
import chapter12.Functor
import java.util.Date

interface Applicative<F> : Functor<F> {

    fun <A, B> apply(
        fab: Kind<F, (A) -> B>,
        fa: Kind<F, A>
    ): Kind<F, B> =
        map2(fa, fab) { a, f -> f(a) }

    fun <A> unit(a: A): Kind<F, A>

    override fun <A, B> map(
        fa: Kind<F, A>,
        f: (A) -> B
    ): Kind<F, B> =
        apply(unit(f), fa)

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

interface Monad<F> : Applicative<F> {

    fun <A, B> flatMap(fa: Kind<F, A>, f: (A) -> Kind<F, B>): Kind<F, B> =
        join(map(fa, f))

    //tag::init1[]
    fun <A> join(ffa: Kind<F, Kind<F, A>>): Kind<F, A>
    //end::init1[]

    override fun <A, B> map(
        fa: Kind<F, A>,
        f: (A) -> B
    ): Kind<F, B> =
        flatMap(fa) { a -> unit(f(a)) }

    override fun <A, B, C> map2(
        fa: Kind<F, A>,
        fb: Kind<F, B>,
        f: (A, B) -> C
    ): Kind<F, C> =
        flatMap(fa) { a -> map(fb) { b -> f(a, b) } }
}

fun listing3() {
    //tag::init2[]
    val F: Applicative<ForOption> = TODO()

    val employee = "Alice"
    val departments: Map<String, String> = mapOf("Alice" to "Tech") // <1>
    val salaries: Map<String, Double> = mapOf("Alice" to 100_000.00) // <2>

    val o: Option<String> =
        F.map2(
            departments[employee].toOption(),
            salaries[employee].toOption()
        ) { dept, salary ->
            "$employee in $dept makes $salary per year." // <3>
        }.fix() // <4>
    //end::init2[]
}

fun listing4() {
    val F: Applicative<ForOption> = TODO()

    //tag::init3[]
    val employee = "Bob"
    val idsByName: Map<String, Int> = mapOf("Bob" to 101) // <1>
    val departments: Map<Int, String> = mapOf(101 to "Sales") // <2>
    val salaries: Map<Int, Double> = mapOf(101 to 100_000.00) // <3>

    val o: Option<String> =
        idsByName[employee].toOption().flatMap { id -> // <4>
            F.map2(
                departments[id].toOption(),
                salaries[id].toOption()
            ) { dept, salary ->
                "$employee in $dept makes $salary per year."
            }.fix()
        }
    //end::init3[]
}

@higherkind
class Parser<A>(val a: A) : ParserOf<A>

fun listing5() {
    //tag::init4[]
    data class Row(val date: Date, val temp: Double)
    //end::init4[]

    fun <A> Parser<A>.sep(s: String): Parser<List<Row>> = TODO()

    //tag::init5[]
    val F: Applicative<ForParser> = TODO()

    val date: Parser<Date> = TODO()
    val temp: Parser<Double> = TODO()

    val row: Parser<Row> = F.map2(date, temp) { d, t -> Row(d, t) }.fix()
    val rows: Parser<List<Row>> = row.sep("\n")
    //end::init5[]
}

fun listing6() {
    data class Row(val date: Date, val temp: Double)

    fun <A> Parser<A>.sep(s: String): Parser<List<Row>> = TODO()

    //tag::init6[]
    val F: Monad<ForParser> = TODO()

    val header: Parser<Parser<Row>> = TODO()
    val rows: Parser<List<Row>> =
        F.flatMap(header) { row: Parser<Row> -> row.sep("\n") }.fix()
    //end::init6[]
}

package chapter4.sec3

sealed class List<out A>

private fun <A> length(xs: List<A>): Int = TODO()

private fun List<Double>.sum(): Double = TODO()

private fun List<Double>.isEmpty(): Boolean = TODO()

fun <A> List<A>.size(): Int = TODO()

//tag::init1[]
sealed class Option<out A>

data class Some<out A>(val get: A) : Option<A>()

object None : Option<Nothing>()
//end::init1[]

val listing1 = {
    //tag::init2[]
    fun mean(xs: List<Double>): Option<Double> =
        if (xs.isEmpty()) None // <1>
        else Some(xs.sum() / xs.size()) //<2>
    //end::init2[]
}

fun <A, B> Option<A>.map(f: (A) -> B): Option<B> = TODO()

fun <A, B> Option<A>.flatMap(f: (A) -> Option<B>): Option<B> = TODO()

fun <A> Option<A>.getOrElse(default: () -> A): A = TODO()

fun <A> Option<A>.filter(f: (A) -> Boolean): Option<A> = TODO()

//tag::init3[]
data class Employee(
    val name: String,
    val department: String,
    val manager: Option<String>
)

fun lookupByName(name: String): Option<Employee> = TODO()

fun timDepartment(): Option<String> =
    lookupByName("Tim").map { it.department }
//end::init3[]

//tag::init4[]
val unwieldy: Option<Option<String>> =
    lookupByName("Tim").map { it.manager }
//end::init4[]

//tag::init5[]
val manager: String = lookupByName("Tim")
    .flatMap { it.manager }
    .getOrElse { "Unemployed" }
//end::init5[]

//tag::init6[]
val dept: String = lookupByName("Tim")
    .map { it.department }
    .filter { it != "Accounts" }
    .getOrElse { "Unemployed" }
//end::init6[]

//tag::init7[]
fun <A, B> lift(f: (A) -> B): (Option<A>) -> Option<B> =
    { oa -> oa.map(f) }
//end::init7[]

//tag::init8[]
val absO: (Option<Double>) -> Option<Double> =
    lift { kotlin.math.abs(it) }
//end::init8[]

//tag::init9[]
/**
 * Top secret formula for computing an annual car
 * insurance premium from two key factors.
 */
fun insuranceRateQuote(
    age: Int,
    numberOfSpeedingTickets: Int
): Double = TODO()
//end::init9[]

private fun <A, B, C> map2(
    oa: Option<A>,
    ob: Option<B>,
    f: (A, B) -> C
): Option<C> = TODO()

//tag::init10[]
fun parseInsuranceRateQuote(
    age: String,
    speedingTickets: String
): Option<Double> {

    val optAge: Option<Int> = catches { age.toInt() }

    val optTickets: Option<Int> =
        catches { speedingTickets.toInt() }

    //tag::secondsolution[]
    return map2(optAge, optTickets) { a, t ->
        insuranceRateQuote(a, t)
    }
    //end::secondsolution[]
    //tag::firstsolution[]
    //return insuranceRateQuote(optAge, optTickets) <1>
    //end::firstsolution[]
}

//tag::catches[]
fun <A> catches(a: () -> A): Option<A> = // <2>
    try {
        Some(a()) // <3>
    } catch (e: Throwable) { // <4>
        None
    }
//end::catches[]
//end::init10[]

fun <A> sequence(xs: List<Option<A>>): Option<List<A>> = TODO()

fun <A, B> List<A>.map(f: (A) -> B): List<B> = TODO()

//tag::init11[]
fun parseInts(xs: List<String>): Option<List<Int>> =
    sequence(xs.map { str -> catches { str.toInt() } })
//end::init11[]

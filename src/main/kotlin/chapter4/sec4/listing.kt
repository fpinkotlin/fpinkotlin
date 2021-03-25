package chapter4.sec4

import chapter3.List
import arrow.core.extensions.fx
import chapter4.sec3.insuranceRateQuote

//tag::init1[]
sealed class Either<out E, out A>

data class Left<out E>(val value: E) : Either<E, Nothing>()

data class Right<out A>(val value: A) : Either<Nothing, A>()
//end::init1[]

fun <A> List<A>.size(): Int = TODO()

fun List<Double>.sum(): Double = TODO()

fun <A> List<A>.isEmpty(): Boolean = TODO()

//tag::init2[]
fun mean(xs: List<Double>): Either<String, Double> =
    if (xs.isEmpty())
        Left("mean of empty list!")
    else Right(xs.sum() / xs.size())
//end::init2[]

//tag::init3[]
fun safeDiv(x: Int, y: Int): Either<Exception, Int> =
    try {
        Right(x / y)
    } catch (e: Exception) {
        Left(e)
    }
//end::init3[]

//tag::init4[]
fun <A> catches(a: () -> A): Either<Exception, A> =
    try {
        Right(a())
    } catch (e: Exception) {
        Left(e)
    }
//end::init4[]

//tag::init5[]
suspend fun String.parseToInt(): arrow.core.Either<Throwable, Int> = // <1>
    arrow.core.Either.catch { this.toInt() } // <2>

suspend fun parseInsuranceRateQuote( // <3>
    age: String,
    numberOfSpeedingTickets: String
): arrow.core.Either<Throwable, Double> {
    val ae = age.parseToInt() // <4>
    val te = numberOfSpeedingTickets.parseToInt()
    return arrow.core.Either.fx { // <5>
        val a = ae.bind() // <6>
        val t = te.bind()
        insuranceRateQuote(a, t) // <7>
    }
}
//end::init5[]

// boilerplate developed in exercises
fun <E, A, B, C> map2(
    ae: Either<E, A>,
    be: Either<E, B>,
    f: (A, B) -> C
): Either<E, C> = TODO()

//tag::init6[]
data class Name(val value: String)
data class Age(val value: Int)
data class Person(val name: Name, val age: Age)

fun mkName(name: String): Either<String, Name> =
    if (name.isBlank()) Left("Name is empty.")
    else Right(Name(name))

fun mkAge(age: Int): Either<String, Age> =
    if (age < 0) Left("Age is out of range.")
    else Right(Age(age))

fun mkPerson(name: String, age: Int): Either<String, Person> =
    map2(mkName(name), mkAge(age)) { n, a -> Person(n, a) }
//end::init6[]
